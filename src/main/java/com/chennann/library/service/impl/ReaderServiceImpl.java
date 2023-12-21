package com.chennann.library.service.impl;

import com.chennann.library.mapper.ReaderMapper;
import com.chennann.library.pojo.Reader;
import com.chennann.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;


    @Override
    public void add(Reader reader) {
        readerMapper.add(reader);
    }

    @Override
    public Reader findById(Integer readerId) {

        return readerMapper.findById(readerId);
    }

    @Override
    public List<Reader> listAllReader() {

        return readerMapper.listAllReader();
    }
}
