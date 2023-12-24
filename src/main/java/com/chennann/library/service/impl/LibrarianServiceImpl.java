package com.chennann.library.service.impl;


import com.chennann.library.mapper.LibrarianMapper;
import com.chennann.library.pojo.Librarian;
import com.chennann.library.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    @Autowired
    private LibrarianMapper librarianMapper;


    @Override
    public void add(Librarian librarian) {
        librarianMapper.add(librarian);
    }

    @Override
    public Librarian findLibrarian(String librarianNumber, String name) {
        Librarian librarian = librarianMapper.findLibrarian(librarianNumber, name);
        return librarian;
    }

    @Override
    public Librarian findByLibrarianId(Integer id) {

        Librarian librarian = librarianMapper.findByLibrarianId(id);
        return librarian;
    }
}
