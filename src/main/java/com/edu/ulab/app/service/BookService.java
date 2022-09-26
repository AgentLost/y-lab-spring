package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    void deleteAllByUserId(Long userId);

    List<BookDto> getAllByUserId(Long userId);
}
