package com.chennann.library.service;

import com.chennann.library.pojo.Librarian;

import java.util.List;

public interface LibrarianService {
    void add(Librarian librarian);

    Librarian findLibrarian(String librarianNumber, String name);

    Librarian findByLibrarianId(Integer id);

    List<Librarian> list();
}
