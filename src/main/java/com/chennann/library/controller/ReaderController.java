package com.chennann.library.controller;

import com.chennann.library.anno.RequireRole;
import com.chennann.library.pojo.*;
import com.chennann.library.service.BorrowService;
import com.chennann.library.service.ReaderService;
import com.chennann.library.utils.JwtUtil;
import com.chennann.library.utils.Md5Util;
import com.chennann.library.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<String> login (Integer readerId, String password) {
        //查询
        Reader reader = readerService.findById(readerId);
        //判断是否存在
        if (reader == null) {
            return Result.error("没有找到指定读者🙅");
        }
        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(reader.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", reader.getReaderId());
            claims.put("username", reader.getName());
            claims.put("role", "reader");
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @RequireRole("librarian")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody @Validated Reader reader) {

        readerService.add(reader);
        return Result.success(reader.getReaderId());
    }


    @RequireRole("librarian")
    @GetMapping("/list")
    public Result<PageBean<Reader>> listAllReader(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer readerId,
            @RequestParam(required = false) String name
    ) {
        PageBean<Reader> pg = readerService.listAllReader(pageNum, pageSize, readerId, name);
        return Result.success(pg);
    }


    @GetMapping("/listborrows")
    public Result<PageBean<Borrow>> listBorrowsByReaderId(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer readerId,
            @RequestParam(required = false) Integer status
    ) {

        PageBean<Borrow> pg = borrowService.listBorrowsByReaderIdAndStatus(pageNum, pageSize, readerId, status);
        return Result.success(pg);
    }

    @RequireRole("librarian")
    @DeleteMapping("/delete")
    public Result deleteReaderById(Integer readerId) {
        readerService.deleteReaderById(readerId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateReader(@RequestBody @Validated Reader reader) {
        readerService.updateReader(reader);
        return Result.success();
    }

    @GetMapping("/readerInfo")
    public Result<Reader> readerInfo() {

        Map<String, Object> map= ThreadLocalUtil.get();

        Reader reader = readerService.findById((Integer) map.get("id"));
        return Result.success(reader);
    }
}
