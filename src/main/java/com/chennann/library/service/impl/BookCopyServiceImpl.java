package com.chennann.library.service.impl;

import com.chennann.library.mapper.BookCopyMapper;
import com.chennann.library.pojo.Book;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.Borrow;
import com.chennann.library.pojo.PageBean;
import com.chennann.library.service.BookCopyService;
import com.chennann.library.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Autowired
    private BookCopyMapper bookCopyMapper;

    @Override
    public void toDefaultLocation(Book book, Integer oldNumber) {
        int tot = book.getCopies();
        List<BookCopy> bookCopiesList = new ArrayList<>();

        Map<String, Object> map = ThreadLocalUtil.get();
        String librarianNumber = (String) map.get("number");

        for (int i = 1; i <= tot; i++) {
            bookCopiesList.add(new BookCopy(
                    null,
                    "C" + book.getIsbn().substring(6, 9) + "." + (oldNumber + i),
                    book.getIsbn(),
                    "图书流通室",
                    "未借出",
                    librarianNumber
            ));
        }

        bookCopyMapper.toDefaultLocation(bookCopiesList);
    }

    @Override
    public BookCopy findById(String bookId) {

        return bookCopyMapper.findById(bookId);
    }

    @Override
    public void allocate(BookCopy bookCopy) {

        bookCopyMapper.allocate(bookCopy);

    }

//    @Override
//    public List<BookCopy> findCopies(String bookName, Integer status) {
//
//        return bookCopyMapper.findCopies(bookName, status);
//    }

    @Override
    public PageBean<BookCopy> findCopies(Integer pageNum, Integer pageSize, String bookName, Integer status) {

        PageBean<BookCopy> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<BookCopy> as = bookCopyMapper.findCopies(bookName, status);
        Page<BookCopy> p = (Page<BookCopy>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;


    }

    @Override
    public void record(Borrow borrow) {
        bookCopyMapper.record(borrow);
    }

    @Override
    public void returnCopy(String bookId, String status) {
        bookCopyMapper.returnCopy(bookId, status);
    }

    @Override
    public void changeCopyStatus(String bookId, String s) {
        bookCopyMapper.changeCopyStatus(bookId, s);
    }

}
