package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;

import java.util.List;

public interface BookStorage {
    Book createBook(Book book);

    Book updateBook(Book book);

    Book getBookById(Long id);

    void deleteBookById(Long id);

    void deleteAllByUserId(Long userId);

    boolean existsById(Long id);

    List<Book> getAllByUserId(Long userId);
}
