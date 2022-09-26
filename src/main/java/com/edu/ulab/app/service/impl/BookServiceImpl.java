package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dao.BookDAO;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookDAO bookDAO, BookMapper bookMapper) {
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(@NotNull BookDto bookDto) {
        Book book = bookDAO.createBook(bookDto);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(@NotNull BookDto bookDto) {
        Book book;
        if(bookDAO.existsById(bookDto.getId())){
            book = bookDAO.updateBook(bookDto);
        }else{
            book = bookDAO.createBook(bookDto);
        }

        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        if(!bookDAO.existsById(id)){
            throw new NotFoundException("book with this id: " + id + " not found");
        }

        return bookMapper.bookToBookDto(
                bookDAO.getBookById(id));
    }

    @Override
    public void deleteBookById(Long id) {
        if(!bookDAO.existsById(id)){
            throw new NotFoundException("book with this id: " + id + " not found");
        }
        bookDAO.deleteBookById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        bookDAO.deleteAllByUserId(userId);
    }

    @Override
    public List<BookDto> getAllByUserId(Long userId) {
        return bookDAO.getBooksByUserId(userId).stream()
                .map(bookMapper::bookToBookDto)
                .toList();
    }
}
