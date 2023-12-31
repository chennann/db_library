package com.chennann.library.controller;

import com.chennann.library.anno.RequireRole;
import com.chennann.library.pojo.*;
import com.chennann.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private EmailService emailService;


    @RequireRole("librarian")
    @PostMapping("/record")
    public Result record(@RequestBody @Validated Borrow borrow) {
//        System.out.println(borrow);
        //检查借阅天数
        if (borrow.getDay()==10 && (borrow.getHour()>0 || borrow.getMinutes()>0)) {
            return Result.error("借阅时间超过十天，借阅失败☹️");
        }

        //检查图书状态
        BookCopy copy = bookCopyService.findById(borrow.getBookId());
        if ("已借出".equals(copy.getStatus()) || "不外借".equals(copy.getStatus()) ||"已预约".equals(copy.getStatus())) {
            return Result.error("书本状态不正确");
        }

        //检查读者借书册数
        PageBean<Borrow> borrowList = borrowService.listNotReturnedByReaderId(1, 5, borrow.getReaderId());
        if (borrowList.getTotal()>=10) {
            return Result.error("借阅书籍超过10本，借阅失败");
        }


        borrowService.record(borrow);
        bookCopyService.record(borrow);
        return Result.success();
    }


    @RequireRole("librarian")
    @PostMapping("/returnCopy")
    public Result<Borrow> returnCopy (@RequestBody Map<String, String> map) {
        String borrowingId = map.get("borrowingId");
        String bookId = map.get("bookId");

        Borrow borrow = borrowService.findBorrowById(borrowingId);
        if (!bookId.equals(borrow.getBookId())) {
            return Result.error("没有对应借阅记录");
        }

        BookCopy bookCopy = bookCopyService.findById(bookId);
        if (bookCopy == null) {
            return Result.error("找不到对应的书");
        }
        String ISBN = bookCopy.getIsbn();


        if (borrow.getReturnTime()!=null) {
            return Result.error("该书已还");
        }

        borrowService.returnCopy(borrowingId);

        List<Reservation> rs = reservationService.findByISBN(ISBN);

        if (rs.size()>0) {
            bookCopyService.returnCopy(bookId, "已预约");
            Reservation r = rs.get(0);
            r.setBookId(bookId);
            reservationService.setBookId(r);
            Reader reader = readerService.findById(r.getReaderId());
            String email = reader.getEmail();
            emailService.sendEmail(
                    reader.getEmail(),
                    "预约提醒",
                    "亲爱的 " + reader.getReaderId() + " 号读者, 您预约的图书（ISBN） " + ISBN + " 现在可以借阅 "
            );
        }
        else {
            bookCopyService.returnCopy(bookId, "未借出");
        }

        LocalDateTime dueTime = borrow.getDueTime();
        LocalDateTime returnTime = LocalDateTime.now();

        long hoursOverdue = ChronoUnit.MINUTES.between(dueTime, returnTime);
        if (hoursOverdue<0) {
            borrow.setFine(0);
        }
        else {
            long fine = hoursOverdue * 2;
            borrow.setFine(fine);
        }

        return Result.success(borrow);
    }
}
