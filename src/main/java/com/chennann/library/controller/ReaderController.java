package com.chennann.library.controller;

import com.chennann.library.pojo.Borrow;
import com.chennann.library.pojo.Reader;
import com.chennann.library.pojo.Result;
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
    public Result<Integer> add (@RequestBody @Validated Reader reader) {
        readerService.add(reader);
        return Result.success(reader.getReaderId());
    }

    @GetMapping("/list")
    public Result<List<Reader>> listAllReader () {
        List<Reader> rs = readerService.listAllReader();
        return Result.success(rs);

    }

    @GetMapping("/listborrows")
    public Result<List<Borrow>> listBorrowsByReaderId (
            @RequestParam(required = false) Integer readerId,
            @RequestParam Integer status
            ) {
        if (readerId == null ) {
            List<Borrow> bs = borrowService.listAllBorrowsByStatus(status);
            return Result.success(bs);
        }
        if (status == 0) {
            List<Borrow> bs = borrowService.listBorrowsByReaderId(readerId);
            return Result.success(bs);
        }
        else {
            List<Borrow> bs = borrowService.listNotReturnedByReaderId(readerId);
            return Result.success(bs);
        }
    }


}
