package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class BooksStorageImpl implements BookStorage{
    private final long START_ID_VALUE = -1;

    private long CURRENT_ID_VALUE;

    private final Map<Long, Book> BOOK_DATA = new HashMap<>();

    @PostConstruct
    public void init(){
        CURRENT_ID_VALUE = START_ID_VALUE;
    }

    public void grow(){
        CURRENT_ID_VALUE++;
    }

    @Override
    public Book createBook(Book book) {
        grow();
        book.setId(CURRENT_ID_VALUE);
        BOOK_DATA.put(CURRENT_ID_VALUE, book);
        return BOOK_DATA.get(CURRENT_ID_VALUE);
    }

    @Override
    public Book updateBook(@NotNull Book book) {
        return BOOK_DATA.put(book.getId(), book);
    }

    @Override
    @Nullable
    public Book getBookById(Long id) {
        return BOOK_DATA.get(id);
    }

    @Override
    public void deleteBookById(Long id) {
        if(BOOK_DATA.remove(id) == null){
            throw new NotFoundException("book with this id: " + id + " not found");
        }
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        List<Long> booksId= BOOK_DATA.values()
                .stream()
                .filter(book -> book.getUserId().equals(userId))
                .map(Book::getId)
                .toList();

        booksId.forEach(BOOK_DATA::remove);
    }

    @Override
    public boolean existsById(Long id){
        return BOOK_DATA.containsKey(id);
    }


    @Override
    public List<Book> getAllByUserId(Long id) {
        return BOOK_DATA.values()
                .stream()
                .filter(book -> Objects.equals(book.getUserId(), id))
                .toList();
    }
}
