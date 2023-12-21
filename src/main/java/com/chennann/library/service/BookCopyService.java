package com.chennann.library.service;

import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.Borrow;

import java.util.List;

public interface BookCopyService {
    void toDefaultLocation(Book book, Integer oldNumber);

    BookCopy findById(String bookId);

    void allocate(BookCopy bookCopy);

    List<BookCopy> findCopies(String bookName, Integer status);

    void record(Borrow borrow);

    void returnCopy(String bookId, String status);

    void changeCopyStatus(String bookId, String s);
}
