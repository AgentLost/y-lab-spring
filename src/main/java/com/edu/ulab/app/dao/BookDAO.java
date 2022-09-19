package com.edu.ulab.app.dao;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;

import java.util.List;

public interface BookDAO {
    Book createBook(BookDto bookDto);

    Book updateBook(BookDto bookDto);

    Book getBookById(Long id);

    boolean existsById(Long id);

    void deleteBookById(Long id);

    void deleteAllByUserId(Long userId);

    List<Book> getBooksByUserId(Long id);
}
