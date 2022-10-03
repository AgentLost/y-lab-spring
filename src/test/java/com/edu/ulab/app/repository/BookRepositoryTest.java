package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Тесты репозитория {@link BookRepository}.
 */
@SystemJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    //create
    @DisplayName("Save book and user. Select count = 2")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void findAllBadges_thenAssertDmlCount() {
        //Given

        Person person = new Person();
        person.setAge(11);
        person.setTitle("test");
        person.setFullName("test");

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setAuthor("test");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(savedPerson);

        //When
        Book result = bookRepository.save(book);

        //Then
        assertThat(result.getPageCount()).isEqualTo(1000);
        assertThat(result.getTitle()).isEqualTo("test");
        assertSelectCount(2);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    //update
    @DisplayName("Update book. Select count = 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updateAllBadges_thenAssertDmlCount() {
        //Given
        Book updateBook = new Book();
        updateBook.setId(2002L);
        updateBook.setAuthor("nik");
        updateBook.setTitle("test");
        updateBook.setPageCount(233);
        //When
        Book result = bookRepository.save(updateBook);
        //Then
        assertThat(result.getPageCount()).isEqualTo(233);
        assertThat(result.getTitle()).isEqualTo("test");
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // get
    @DisplayName("Get book. Select count = 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getBadges_thenAssertDmlCount() {
        //Given
        Long bookId = 2002L;
        //When
        Book result = bookRepository.findById(bookId).orElse(new Book());
        //Then
        assertThat(result.getPageCount()).isEqualTo(5500);
        assertThat(result.getTitle()).isEqualTo("test");
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // get all
    @DisplayName("Get books by user id. Select count = 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getAllBadgesByUserId_thenAssertDmlCount() {
        //Given
        Long personId = 1001L;
        //When
        List<Book> result = bookRepository.findAllByPerson(personId);
        //Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1).getId()).isEqualTo(3003);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // delete

    @DisplayName("Delete book. Select count = 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deleteBadges_thenAssertDmlCount() {
        //Given
        Long bookId = 2002L;
        //When
        bookRepository.deleteById(bookId);
        Book book = bookRepository.findById(bookId).orElse(new Book());
        //Then
        assertThat(book.getTitle()).isEqualTo(null);
        assertThat(book.getId()).isEqualTo(0);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
    // * failed


    // example failed test

    @DisplayName("Failed get book. Select count = 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void failedGetPerson_thenAssertDmlCount() {
        //Given
        Long bookId = 1000L;
        //When
        Book result = bookRepository.findById(bookId).orElse(null);

        //Then
        assertThatThrownBy(() -> result.getPageCount())
                .isInstanceOf(NullPointerException.class);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

}
