package com.chennann.library.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @NotEmpty
    private String isbn;
    @NotEmpty
    private String title;
    private String author;
    private String publisher;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishdate;
    @NotNull
    private Integer copies;
    private String librarianNumber;

}
