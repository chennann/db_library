package com.chennann.library.service;

import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.Borrow;
import com.chennann.library.pojo.PageBean;

import java.util.List;

public interface BookCopyService {
    void toDefaultLocation(Book book, Integer oldNumber);

    BookCopy findById(String bookId);

    void allocate(BookCopy bookCopy);

    PageBean<BookCopy> findCopies(Integer pageNum, Integer pageSize, String bookName, Integer status, String bookId);

    void record(Borrow borrow);

    void returnCopy(String bookId, String status);

    void changeCopyStatus(String bookId, String s);
}
