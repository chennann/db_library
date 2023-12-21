package com.chennann.library.service;

import com.chennann.library.pojo.Book;

import java.util.List;

public interface BookService {
    List<Book> listAllBooks();


    void addBook(Book book);

    List<Book> findBooks(String title, String author, String isbn);

    void updateCopies(Book book);
}
