package com.chennann.library.service.impl;

import com.chennann.library.mapper.ReservationMapper;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.Reader;
import com.chennann.library.pojo.Reservation;
import com.chennann.library.service.BookCopyService;
import com.chennann.library.service.ReaderService;
import com.chennann.library.service.ReservationService;
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
    public void cancel(Reservation reservation) {
        reservationMapper.cancel(reservation);
    }

    @Override
    public void setBookId(Reservation r) {
        reservationMapper.setBookId(r);
    }

    @Override
    public List<Reservation> findDueReservations() {

        return reservationMapper.findDueReservations();
    }

    @Override
    public List<Reservation> listAllReservation() {

        return reservationMapper.listAllReservation();
    }
}
