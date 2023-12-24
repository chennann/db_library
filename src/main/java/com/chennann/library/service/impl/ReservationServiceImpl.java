package com.chennann.library.service.impl;

import com.chennann.library.mapper.ReservationMapper;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.PageBean;
import com.chennann.library.pojo.Reader;
import com.chennann.library.pojo.Reservation;
import com.chennann.library.service.BookCopyService;
import com.chennann.library.service.ReaderService;
import com.chennann.library.service.ReservationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ReaderService readerService;


    @Override
    public void add(Reservation reservation) {
        Period daysToAdd = Period.ofDays(reservation.getDays());

        reservation.setReservationTime(LocalDateTime.now());
        LocalDateTime dueTime = reservation.getReservationTime().plus(daysToAdd);

        reservation.setReservationDueTime(dueTime);


        reservationMapper.add(reservation);
    }

    @Override
    public List<Reservation> findByISBN(String isbn) {

        return reservationMapper.findByISBN(isbn);
    }

    @Override
    public void cancel(Integer readerId, String isbn) {
        reservationMapper.cancel(readerId, isbn);
    }

    @Override
    public void setBookId(Reservation r) {
        reservationMapper.setBookId(r);
    }

    @Override
    public List<Reservation> findDueReservations() {

        return reservationMapper.findDueReservations();
    }

//    @Override
//    public List<Reservation> listAllReservation() {
//
//        return reservationMapper.listAllReservation();
//    }
    @Override
    public PageBean<Reservation> listAllReservation(Integer pageNum, Integer pageSize, Integer readerId) {

        PageBean<Reservation> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Reservation> as = reservationMapper.listAllReservation(readerId);
        Page<Reservation> p = (Page<Reservation>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
