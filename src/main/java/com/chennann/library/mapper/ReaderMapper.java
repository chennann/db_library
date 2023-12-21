package com.chennann.library.mapper;

import com.chennann.library.pojo.Reader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReaderMapper {


    void add(Reader reader);

    @Select("select * from readers where readerId=#{readerId}")
    Reader findById(Integer readerId);

    @Select("select * from readers")
    List<Reader> listAllReader();
}
