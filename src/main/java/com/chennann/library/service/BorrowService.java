package com.chennann.library.service;

import com.chennann.library.pojo.Borrow;
import com.chennann.library.pojo.PageBean;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowService {
    void record(Borrow borrow);

    //List<Borrow> listBorrowsByReaderId(Integer readerId);
    PageBean<Borrow> listBorrowsByReaderId(Integer pageNum, Integer pageSize, Integer readerId);

//    List<Borrow> listNotReturnedByReaderId(Integer readerId);
    PageBean<Borrow> listNotReturnedByReaderId(Integer pageNum, Integer pageSize, Integer readerId);

    List<Borrow> findDueBorrows();

    void returnCopy(String borrowingId);

    Borrow findBorrowById(String borrowingId);


    //    List<Borrow> listAllBorrowsByStatus(Integer status);
    PageBean<Borrow> listAllBorrowsByStatus(Integer pageNum, Integer pageSize,Integer status);
}
