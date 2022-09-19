package com.edu.ulab.app.dao.impl;

import com.edu.ulab.app.dao.BookDAO;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAOImpl implements BookDAO {
    private final BookStorage bookStorage;

    private final BookMapper bookMapper;

    public BookDAOImpl(BookStorage bookStorage, BookMapper bookMapper) {
        this.bookStorage = bookStorage;
        this.bookMapper = bookMapper;
    }

    @Override
    public Book createBook(BookDto bookDto) {
        return bookStorage.createBook(
                bookMapper.bookDtoToUser(bookDto));
    }

    @Override
    public Book updateBook(BookDto bookDto) {
        return bookStorage.updateBook(
                bookMapper.bookDtoToUser(bookDto));
    }

    @Override
    @Nullable
    public Book getBookById(Long id) {
        return bookStorage.getBookById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return bookStorage.existsById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookStorage.deleteBookById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        bookStorage.deleteAllByUserId(userId);
    }

    @Override
    public List<Book> getBooksByUserId(Long userId) {
        return bookStorage.getAllByUserId(userId);
    }
}
