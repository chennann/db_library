package com.chennann.library.service.impl;

import com.chennann.library.mapper.BorrowMapper;
import com.chennann.library.pojo.Borrow;
import com.chennann.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;


    @Override
    public void record(Borrow borrow) {


        Period daysToAdd = Period.ofDays(borrow.getDay());
        Duration timeToAdd = Duration.ofHours(borrow.getHour()).plusMinutes(borrow.getMinutes());

        borrow.setBorrowTime(LocalDateTime.now());

        LocalDateTime dueTime = borrow.getBorrowTime().plus(daysToAdd).plus(timeToAdd);

        borrow.setDueTime(dueTime);

        borrowMapper.record(borrow);
    }

    @Override
    public List<Borrow> listBorrowsByReaderId(Integer readerId) {

        return borrowMapper.listBorrowsByReaderId(readerId);
    }

    @Override
    public List<Borrow> listNotReturnedByReaderId(Integer readerId) {

        return borrowMapper.listNotReturnedByReaderId(readerId);
    }

    @Override
    public List<Borrow> findDueBorrows() {

        return borrowMapper.findDueBorrows();
    }

    @Override
    public void returnCopy(String borrowingId) {

        borrowMapper.returnCopy(borrowingId);
    }

    @Override
    public Borrow findBorrowById(String borrowingId) {

        return borrowMapper.findBorrowById(borrowingId);
    }

    @Override
    public List<Borrow> listAllBorrowsByStatus(Integer status) {

        return borrowMapper.listAllBorrowsByStatus(status);
    }


}
