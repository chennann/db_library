package com.chennann.library.mapper;


import com.chennann.library.pojo.Librarian;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LibrarianMapper {

    @Insert("insert into librarians (librarianNumber, name) " +
            " values (#{librarianNumber}, #{name})")
    void add(Librarian librarian);

    @Select("select * from librarians where librarianNumber=#{librarianNumber} and BINARY name=#{name}")
    Librarian findLibrarian(String librarianNumber, String name);

    @Select("select * from librarians where librarianId =#{id}")
    Librarian findByLibrarianId(Integer id);

    @Select("select * from librarians")
    List<Librarian> list();
}
