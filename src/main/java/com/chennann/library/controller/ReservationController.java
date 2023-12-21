package com.chennann.library.controller;

import com.chennann.library.pojo.Reader;
import com.chennann.library.pojo.Reservation;
import com.chennann.library.pojo.Result;
import com.chennann.library.service.BookCopyService;
import com.chennann.library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BookCopyService bookCopyService;

    @PostMapping("/add")
    public Result add (@RequestBody @Validated Reservation reservation) {
        reservationService.add(reservation);
        return Result.success();

    }

    @DeleteMapping("/cancel")
    public Result cancel (@RequestBody @Validated Reservation reservation) {
        if (reservation.getBookId() != null) {
            bookCopyService.changeCopyStatus(reservation.getBookId(), "未借出");
        }
        reservationService.cancel(reservation);
        return Result.success();
    }

    @GetMapping ("/list")
    public Result<List<Reservation>> listAllReservation () {
        List<Reservation> rs = reservationService.listAllReservation();
        return Result.success(rs);
    }
}
