package com.chennann.library.service;

import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.PageBean;

import java.util.List;

public interface BookService {
    List<Book> listAllBooks();


    void addBook(Book book);

    PageBean<Book> findBooks(Integer pageNum, Integer pageSize, String title, String author, String isbn);

    void updateCopies(Book book);
}
