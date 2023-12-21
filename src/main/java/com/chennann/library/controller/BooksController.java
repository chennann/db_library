package com.chennann.library.controller;


import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.Result;
import com.chennann.library.service.BookCopyService;
import com.chennann.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("/find")
    public Result<List<Book>> findBooks (
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn
    ) {
        List<Book> bc = bookService.findBooks(title, author, isbn);
        return Result.success(bc);
    }

    @GetMapping("/listall")
    public Result<List<Book>> listAllBooks () {
        List<Book> bc = bookService.listAllBooks();
        return Result.success(bc);
    }

    @PostMapping("/add")
    public Result addBook (@RequestBody @Validated Book book) {
        List<Book> bc = bookService.findBooks(book.getTitle(), book.getAuthor(), book.getIsbn());
        if (bc.isEmpty()) {
            //没有图书，新增图书
            bookService.addBook(book);
            bookCopyService.toDefaultLocation(book, 0);
        }
        else {
            //已有图书，新增册数
            int increaseNumber = book.getCopies();
            int oldNumber = bc.get(0).getCopies();
            int newCopyNumber = increaseNumber + oldNumber;

            bookCopyService.toDefaultLocation(book, oldNumber);

            book.setCopies(newCopyNumber);

            bookService.updateCopies(book);



        }
        return Result.success();
    }
}
