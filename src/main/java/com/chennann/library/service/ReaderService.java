package com.chennann.library.service;

import com.chennann.library.pojo.Reader;

import java.util.List;

public interface ReaderService {
    void add(Reader reader);


    Reader findById(Integer readerId);

    List<Reader> listAllReader();
}
