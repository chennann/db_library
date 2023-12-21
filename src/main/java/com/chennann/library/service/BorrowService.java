package com.chennann.library.service;

import com.chennann.library.pojo.Borrow;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowService {
    void record(Borrow borrow);

    List<Borrow> listBorrowsByReaderId(Integer readerId);

    List<Borrow> listNotReturnedByReaderId(Integer readerId);

    List<Borrow> findDueBorrows();

    void returnCopy(String borrowingId);

    Borrow findBorrowById(String borrowingId);


    List<Borrow> listAllBorrowsByStatus(Integer status);
}
