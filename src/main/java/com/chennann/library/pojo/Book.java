package com.chennann.library.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
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
    @NotEmpty(groups = Add.class)
    private String title;
    @NotEmpty(groups = Add.class)
    private String author;
    @NotEmpty(groups = Add.class)
    private String publisher;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishdate;
    @NotNull(groups = Add.class)
    private Integer copies;
    private String librarianNumber;

    public interface Add extends Default {

    }
    public interface precheck extends Default {

    }
}

