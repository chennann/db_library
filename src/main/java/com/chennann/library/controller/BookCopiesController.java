package com.chennann.library.controller;

import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.PageBean;
import com.chennann.library.pojo.Result;
import com.chennann.library.service.BookCopyService;
import com.chennann.library.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/copies")
public class BookCopiesController {

    @Autowired
    private BookCopyService bookCopyService;

    @PutMapping("/allocate")
    public Result allocate (@RequestBody @Validated BookCopy bookCopy) {
        String bookId = bookCopy.getBookId();
        String targetLocation = bookCopy.getLocation();

        BookCopy bc = bookCopyService.findById(bookId);

        if (targetLocation.equals(bc.getLocation())) {
            return Result.error("已经在目标位置");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String librarianNumber = (String) map.get("number");

        if (("图书阅览室".equals(targetLocation) && "未借出".equals(bc.getStatus()))) {

            bookCopy.setStatus("不外借");
            bookCopy.setLibrarianNumber(librarianNumber);
            bookCopyService.allocate(bookCopy);

        }
        else if ("图书流通室".equals(targetLocation)) {

            bookCopy.setStatus("未借出");
            bookCopy.setLibrarianNumber(librarianNumber);
            bookCopyService.allocate(bookCopy);

        }
        else {
            return Result.error("操作不合法");
        }

        return Result.success();
    }



    @GetMapping("/findcopies")
    public Result<PageBean<BookCopy>> findCopies (
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String bookId
    ) {
        PageBean<BookCopy> pg = bookCopyService.findCopies(pageNum, pageSize, bookName, status, bookId);
        return Result.success(pg);
    }
}
