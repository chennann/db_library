package com.chennann.library.mapper;

import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookCopyMapper {


    void toDefaultLocation(List<BookCopy> bookCopiesList);

    @Select("select * from bookcopies where bookId=#{bookId}")
    BookCopy findById(String bookId);

    @Update("update bookcopies set location = #{location}, status = #{status}, librarianNumber = #{librarianNumber} where bookId = #{bookId}")
    void allocate(BookCopy bookCopy);

//    @Select("SELECT b.title, bc.* FROM bookcopies bc JOIN books b ON bc.isbn = b.isbn WHERE b.title like CONCAT('%', #{bookName}, '%') and bc.status = '未借出'")
    List<BookCopy> findCopies(String bookName, Integer status, String bookId);

    @Update("update bookcopies set status='已借出' where bookId=#{bookId}")
    void record(Borrow borrow);

    @Update("update bookcopies set status=#{status} where bookId=#{bookId}")
    void returnCopy(String bookId, String status);

    @Update("update bookcopies set status=#{s} where bookId=#{bookId}")
    void changeCopyStatus(String bookId, String s);
}
