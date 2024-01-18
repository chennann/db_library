package com.chennann.library.mapper;

import com.chennann.library.pojo.Reader;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReaderMapper {


    void add(Reader reader);

    @Select("select * from readers where readerId=#{readerId}")
    Reader findById(Integer readerId);

//    @Select("select * from readers")
    List<Reader> listAllReader(Integer readerId, String name);

    @Delete("delete from readers where readerId=#{readerId}")
    void deleteReaderById(Integer readerId);

    @Update("update readers set name=#{name},phone=#{phone},email=#{email} where readerId=#{readerId}")
    void updateReader(Reader reader);

    @Select("select name from readers")
    List<String> listReaderNames();

    @Select("select readerId, name, phone, email from readers where name = #{name}")
    Reader findByName(String name);
}
