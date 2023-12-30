package com.chennann.library.mapper;

import com.chennann.library.pojo.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from books;")
    List<Book> listAllBooks();

    @Insert("insert into books (isbn, title, author, publisher, publishdate, copies, librarianNumber) values " +
            "(#{isbn}, #{title}, #{author}, #{publisher}, #{publishdate}, #{copies}, #{librarianNumber})")
    void addBook(Book book);

    List<Book> findBooks(String title, String author, String isbn);

    @Update("update books set copies = #{copies} where isbn = #{isbn}")
    void updateCopies(Book book);

    @Select("select * from books where isbn = #{isbn}")
    Book findBookByISBN(String isbn);
}
