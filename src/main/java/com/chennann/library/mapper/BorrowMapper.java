package com.chennann.library.mapper;

import com.chennann.library.pojo.Borrow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BorrowMapper {

    @Insert("insert into borrowings (readerId, bookId, borrowTime, dueTime) " +
            " values (#{readerId}, #{bookId}, #{borrowTime}, #{dueTime})")
    void record(Borrow borrow);

    @Select("select * from borrowings where readerId=#{readerId}")
    List<Borrow> listBorrowsByReaderId(Integer readerId);

    @Select("select * from borrowings where readerId=#{readerId} and returnTime is NULL")
    List<Borrow> listNotReturnedByReaderId(Integer readerId);

    @Select("select b.*, r.email from borrowings b join readers r on r.readerId = b.readerId where dueTime < now() and returnTime is null")
    List<Borrow> findDueBorrows();

    @Update("update borrowings set returnTime=now() where borrowingId=#{borrowingId}")
    void returnCopy(String borrowingId);

    @Select("select * from borrowings where borrowingId=#{borrowingId}")
    Borrow findBorrowById(String borrowingId);


    List<Borrow> listAllBorrowsByStatus(Integer status);
}
