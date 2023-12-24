package com.chennann.library.controller;

import com.chennann.library.pojo.*;
import com.chennann.library.service.BorrowService;
import com.chennann.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/add")
    public Result<Integer> add(@RequestBody @Validated Reader reader) {
        readerService.add(reader);
        return Result.success(reader.getReaderId());
    }

    //    @GetMapping("/list")
//    public Result<List<Reader>> listAllReader () {
//        List<Reader> rs = readerService.listAllReader();
//        return Result.success(rs);
//    }
    @GetMapping("/list")
    public Result<PageBean<Reader>> listAllReader(Integer pageNum, Integer pageSize) {
        PageBean<Reader> pg = readerService.listAllReader(pageNum, pageSize);
        return Result.success(pg);
    }


    @GetMapping("/listborrows")
    public Result<PageBean<Borrow>> listBorrowsByReaderId(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer readerId,
            @RequestParam Integer status
    ) {
        if (readerId == null) {
            PageBean<Borrow> pg = borrowService.listAllBorrowsByStatus(pageNum, pageSize, status);
            return Result.success(pg);
        }
        if (status == 0) {
            PageBean<Borrow> pg = borrowService.listBorrowsByReaderId(pageNum, pageSize, readerId);
            return Result.success(pg);
        } else {
            PageBean<Borrow> pg = borrowService.listNotReturnedByReaderId(pageNum, pageSize, readerId);
            return Result.success(pg);
        }
    }

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

}
