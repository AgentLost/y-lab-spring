package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
@Primary
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(@NotNull BookDto bookDto) {
        Book book = bookRepository.save(
                bookMapper.bookDtoToBook(bookDto));
        log.info("Create book: {}", book);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto updateBook(@NotNull BookDto bookDto) {
        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new NotFoundException(""));

        Book updateBook = bookRepository.save(
                bookMapper.bookDtoToBook(bookDto));
        log.info("Update book: {}", updateBook);

        return bookMapper.bookToBookDto(updateBook);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("book with this id:" + id + "not found"));
        log.info("Get book: {}", book);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        bookRepository.deleteBooksByPerson( userId);
    }

    @Override
    public List<BookDto> getAllByUserId(Long userId) {
        return bookRepository.findAllByPerson(userId)
                .stream()
                .peek(book -> log.info("Get book: {}", book))
                .map(bookMapper::bookToBookDto)
                .toList();
    }
}
