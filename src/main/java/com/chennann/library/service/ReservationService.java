package com.chennann.library.service;

import com.chennann.library.pojo.PageBean;
import com.chennann.library.pojo.Reservation;

import java.util.List;

public interface ReservationService {

    void add(Reservation reservation);

    List<Reservation> findByISBN(String isbn);

    void cancel(Integer readerId, String isbn);

    void setBookId(Reservation r);

    List<Reservation> findDueReservations();

    PageBean<Reservation> listAllReservation(Integer pageNum, Integer pageSize, Integer readerId);
}
