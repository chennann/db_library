package com.chennann.library.service.impl;

import com.chennann.library.mapper.BorrowMapper;
import com.chennann.library.pojo.Borrow;
import com.chennann.library.pojo.PageBean;
import com.chennann.library.pojo.Reader;
import com.chennann.library.service.BorrowService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    //    @Override
//    public List<Borrow> listBorrowsByReaderId(Integer readerId) {
//
//        return borrowMapper.listBorrowsByReaderId(readerId);
//    }
    @Override
    public PageBean<Borrow> listBorrowsByReaderId(Integer pageNum, Integer pageSize, Integer readerId) {

        PageBean<Borrow> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Borrow> as = borrowMapper.listBorrowsByReaderId(readerId);
        Page<Borrow> p = (Page<Borrow>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    //    @Override
//    public List<Borrow> listNotReturnedByReaderId(Integer readerId) {
//
//        return borrowMapper.listNotReturnedByReaderId(readerId);
//    }
    @Override
    public PageBean<Borrow> listNotReturnedByReaderId(Integer pageNum, Integer pageSize, Integer readerId) {

        PageBean<Borrow> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Borrow> as = borrowMapper.listNotReturnedByReaderId(readerId);
        Page<Borrow> p = (Page<Borrow>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
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

    //    @Override
//    public List<Borrow> listAllBorrowsByStatus(Integer status) {
//
//        return borrowMapper.listAllBorrowsByStatus(status);
//    }
    @Override
    public PageBean<Borrow> listAllBorrowsByStatus(Integer pageNum, Integer pageSize, Integer status) {

        PageBean<Borrow> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Borrow> as = borrowMapper.listAllBorrowsByStatus(status);
        Page<Borrow> p = (Page<Borrow>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<Borrow> listAllBorrows(Integer pageNum, Integer pageSize) {

        PageBean<Borrow> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Borrow> as = borrowMapper.listAllBorrows();
        Page<Borrow> p = (Page<Borrow>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<Borrow> listBorrowsByReaderIdAndStatus(Integer pageNum, Integer pageSize, Integer readerId, Integer status) {

        PageBean<Borrow> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<Borrow> as = borrowMapper.listBorrowsByReaderIdAndStatus(readerId, status);
        Page<Borrow> p = (Page<Borrow>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }


}
