package com.chennann.library.service;

import com.chennann.library.pojo.Borrow;
import com.chennann.library.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@EnableScheduling
public class ReminderService {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BookCopyService bookCopyService;

    // 每分钟检查一次
    @Scheduled(fixedRate = 60000)
    public void sendDueDateReminders() {
        List<Borrow> borrows = borrowService.findDueBorrows();
        for (Borrow borrow : borrows) {
            emailService.sendEmail(
                    borrow.getEmail(),
                    "⏰还书提醒⏰",
                    "亲爱的 " + borrow.getReaderId() + " 号读者, 您借阅的图书 " + borrow.getBookId()+ " 原定归还时间: " + borrow.getDueTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 现已超时,请经快归还，否则报警处理。👮🚔🚨法网恢恢，疏而不漏！！🚨🚔👮 "
            );
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanTimeOutReservation () {
        System.out.println("开始清理过期预约");
        List<Reservation> reservations = reservationService.findDueReservations();
        for (Reservation reservation : reservations) {
            if (reservation.getBookId() != null) {
                bookCopyService.changeCopyStatus(reservation.getBookId(), "未借出");
            }
            reservationService.cancel(reservation.getReaderId(), reservation.getIsbn());
        }
    }
}

