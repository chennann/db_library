package com.chennann.library.controller;


import com.chennann.library.anno.RequireRole;
import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.PageBean;
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
    public Result<PageBean<Book>> findBooks (
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn
    ) {
        PageBean<Book> bc = bookService.findBooks(pageNum, pageSize, title, author, isbn);
        return Result.success(bc);
    }


    //listall多余
    @GetMapping("/listall")
    public Result<List<Book>> listAllBooks () {
        List<Book> bc = bookService.listAllBooks();
        return Result.success(bc);
    }

    @RequireRole("librarian")
    @PostMapping("/add/precheck")
    public Result precheck (@RequestBody @Validated Book book) {
        Book b = bookService.findBookByISBN(book.getIsbn());
        if (b != null) {
            return Result.success(b);
        }
        else {
            return Result.success("没有图书");
        }
    }

    @RequireRole("librarian")
    @PostMapping("/add")
    public Result addBook (@RequestBody @Validated Book book) {
        PageBean<Book> bc = bookService.findBooks(1, 5, book.getTitle(), book.getAuthor(), book.getIsbn());
        if (bc.getTotal() == 0) {
            //没有图书，新增图书
            bookService.addBook(book);
            bookCopyService.toDefaultLocation(book, 0);
        }
        else {
            //已有图书，新增册数
            int increaseNumber = book.getCopies();
//            int oldNumber = bc.get(0).getCopies();
            int oldNumber = bc.getItems().get(0).getCopies();
            int newCopyNumber = increaseNumber + oldNumber;

            bookCopyService.toDefaultLocation(book, oldNumber);

            book.setCopies(newCopyNumber);

            bookService.updateCopies(book);



        }
        return Result.success();
    }
}
