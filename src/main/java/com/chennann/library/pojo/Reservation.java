package com.chennann.library.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {

    private Integer reservationId;
    @NotNull
    private Integer readerId;
    @NotEmpty
    private String isbn;
    private LocalDateTime reservationTime;
    @Min(0)@Max(10)
    private Integer days;
    private LocalDateTime reservationDueTime;

    private String bookId;
}
