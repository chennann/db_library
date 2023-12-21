package com.chennann.library.service.impl;

import com.chennann.library.mapper.BookMapper;
import com.chennann.library.pojo.Book;
import com.chennann.library.service.BookService;
import com.chennann.library.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> listAllBooks() {

        return bookMapper.listAllBooks();
    }

    @Override
    public void addBook(Book book) {

        Map<String, Object> map = ThreadLocalUtil.get();
        String librarianNumber = (String) map.get("number");

        book.setLibrarianNumber(librarianNumber);

        bookMapper.addBook(book);

    }

    @Override
    public List<Book> findBooks(String title, String author, String isbn) {

        return bookMapper.findBooks(title, author, isbn);
    }

    @Override
    public void updateCopies(Book book) {
        bookMapper.updateCopies(book);
    }

}
