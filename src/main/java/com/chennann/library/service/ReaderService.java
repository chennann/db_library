package com.chennann.library.service;

import com.chennann.library.pojo.PageBean;
import com.chennann.library.pojo.Reader;

import java.util.List;

public interface ReaderService {
    void add(Reader reader);


    Reader findById(Integer readerId);

    PageBean<Reader> listAllReader(Integer pageNum, Integer pageSize, Integer readerId, String name);

    void deleteReaderById(Integer readerId);

    void updateReader(Reader reader);

    List<String> listReaderNames();

    Reader findByName(String name);
}
