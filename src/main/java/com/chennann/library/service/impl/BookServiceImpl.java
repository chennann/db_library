package com.chennann.library.service.impl;

import com.chennann.library.mapper.BookMapper;
import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.PageBean;
import com.chennann.library.service.BookService;
import com.chennann.library.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    //    @Override
//    public List<Book> findBooks(String title, String author, String isbn) {
//
//        return bookMapper.findBooks(title, author, isbn);
//    }
    @Override
    public PageBean<Book> findBooks(Integer pageNum, Integer pageSize, String title, String author, String isbn) {

        PageBean<Book> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Book> as = bookMapper.findBooks(title, author, isbn);
        Page<Book> p = (Page<Book>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void updateCopies(Book book) {
        bookMapper.updateCopies(book);
    }

}
