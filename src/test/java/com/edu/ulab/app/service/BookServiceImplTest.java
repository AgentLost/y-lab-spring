package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    @DisplayName("Save book. Successfully.")
    void saveBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
    }


    // update
    @Test
    @DisplayName("Update book. Successfully.")
    void updateBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test");
        bookDto.setTitle("test");
        bookDto.setPageCount(1000);

        BookDto bookDtoUpdate = new BookDto();
        bookDtoUpdate.setId(1L);
        bookDtoUpdate.setUserId(1L);
        bookDtoUpdate.setAuthor("test1");
        bookDtoUpdate.setTitle("test1");
        bookDtoUpdate.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test");
        result.setTitle("test");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test");
        book.setAuthor("test");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test");
        savedBook.setAuthor("test");
        savedBook.setPerson(person);

        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setPageCount(1000);
        updateBook.setTitle("test1");
        updateBook.setAuthor("test1");
        updateBook.setPerson(person);

        //when

        when(bookMapper.bookDtoToBook(bookDtoUpdate)).thenReturn(updateBook);
        when(bookRepository.save(updateBook)).thenReturn(updateBook);
        when(bookMapper.bookToBookDto(updateBook)).thenReturn(bookDtoUpdate);


        //then
        BookDto bookDtoResult = bookService.updateBook(bookDtoUpdate);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals("test1", bookDtoResult.getAuthor());
        assertEquals("test1", bookDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Get book. Successfully.")
    void getBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(savedBook));
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.getBookById(bookId);
        assertEquals("test title", bookDtoResult.getTitle());
        assertEquals("test author", bookDtoResult.getAuthor());
    }
    // get all

    @Test
    @DisplayName("Get book by user id. Successfully.")
    void getAllBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        Long personId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        BookDto result2 = new BookDto();
        result2.setId(1L);
        result2.setUserId(1L);
        result2.setAuthor("test author");
        result2.setTitle("test title");
        result2.setPageCount(1000);

        Book savedBook2 = new Book();
        savedBook2.setId(1L);
        savedBook2.setPageCount(1000);
        savedBook2.setTitle("test title");
        savedBook2.setAuthor("test author");
        savedBook2.setPerson(person);

        List<Book> books = new ArrayList<>();
        books.add(savedBook);
        books.add(savedBook2);
        //when

        when(bookRepository.findAllByPerson(personId)).thenReturn(books);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);
        when(bookMapper.bookToBookDto(savedBook2)).thenReturn(result2);

        //then
        List<BookDto> bookDtoResult = bookService.getAllByUserId(personId);
        assertEquals(bookDtoResult.size(), 2);
        assertEquals(bookDtoResult.get(0), result);
    }
    // delete
    @Test
    @DisplayName("Delete book. Successfully.")
    void deleteBook_Test() {
        //given

        Long bookId = 1L;

        //when

        doNothing().when(bookRepository).deleteById(bookId);

        //then
        bookService.deleteBookById(bookId);
    }
    // * failed

    @Test
    @DisplayName("Failed get book. Successfully.")
    void failedGetBook_Test() {
        //given
        Person person  = new Person();
        person.setId(1L);

        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test");
        result.setTitle("test");
        result.setPageCount(1000);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test");
        savedBook.setAuthor("test");
        savedBook.setPerson(person);

        //when

        when(bookRepository.findById(bookId)).thenReturn(null);


        //then
        assertThatThrownBy(() -> bookService.getBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book with id: 1 not found");
    }
}