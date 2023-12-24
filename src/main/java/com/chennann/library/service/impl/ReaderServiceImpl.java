package com.chennann.library.service.impl;

import com.chennann.library.mapper.ReaderMapper;
import com.chennann.library.pojo.BookCopy;
import com.chennann.library.pojo.PageBean;
import com.chennann.library.pojo.Reader;
import com.chennann.library.service.ReaderService;
import com.chennann.library.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

//    @Override
//    public List<Reader> listAllReader() {
//
//        return readerMapper.listAllReader();
//    }

    @Override
    public PageBean<Reader> listAllReader(Integer pageNum, Integer pageSize, Integer readerId, String name) {

        PageBean<Reader> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Reader> as = readerMapper.listAllReader(readerId, name);
        Page<Reader> p = (Page<Reader>) as;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void deleteReaderById(Integer readerId) {
        readerMapper.deleteReaderById(readerId);
    }

    @Override
    public void updateReader(Reader reader) {
        readerMapper.updateReader(reader);
    }
}
