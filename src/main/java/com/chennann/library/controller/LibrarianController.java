package com.chennann.library.controller;

import com.chennann.library.pojo.Librarian;
import com.chennann.library.service.LibrarianService;
import com.chennann.library.pojo.Result;
import com.chennann.library.utils.JwtUtil;
import com.chennann.library.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {


    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/add")
    public Result add (@RequestBody Librarian librarian) {
        librarianService.add(librarian);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login (String librarianNumber, String name) {
        Librarian librarian = librarianService.findLibrarian(librarianNumber, name);
        if (librarian == null) {
            return Result.error("没有找到指定管理员");
        }
        else {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", librarian.getLibrarianId());
            claims.put("number", librarian.getLibrarianNumber());
            claims.put("name", librarian.getName());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
    }

    @GetMapping("/librarianInfo")
    public Result<Librarian> userInfo () {

        Map<String, Object> map= ThreadLocalUtil.get();

        Librarian librarian = librarianService.findByLibrarianId((Integer) map.get("id"));

        return Result.success(librarian);
    }
}
