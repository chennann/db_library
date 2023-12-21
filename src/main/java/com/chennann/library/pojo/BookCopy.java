package com.chennann.library.pojo;


import com.chennann.library.anno.BookCopyLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCopy {


    private String title;
    private String bookId;
    private String isbn;
    @BookCopyLocation
    private String location;
    private String status;
    private String librarianNumber;


}
