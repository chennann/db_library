package com.chennann.library.mapper;

import com.chennann.library.pojo.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {

    @Insert("insert into reservations (readerId, isbn, reservationTime, reservationDueTime) " +
            " values (#{readerId}, #{isbn}, #{reservationTime}, #{reservationDueTime})")
    void add(Reservation reservation);

    @Select("select * from reservations where isbn=#{isbn} and bookId is null order by reservationTime ")
    List<Reservation> findByISBN(String isbn);

    @Delete("delete from reservations where readerId=#{readerId} and isbn=#{isbn}")
    void cancel(Reservation reservation);

    @Update("update reservations set bookId=#{bookId} where reservationId=#{reservationId}")
    void setBookId(Reservation r);

    @Select("select * from reservations where reservationDueTime < now()")
    List<Reservation> findDueReservations();

//    @Select("select * from reservations")
    List<Reservation> listAllReservation(Integer readerId);
}