package com.edu.ulab.app.service.impl;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@Primary
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;
    private final BookMapper bookMapper;

    RowMapper<Book> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new Book(resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getLong("page_count"));
    };

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate, BookMapper bookMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(@NotNull BookDto bookDto) {
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUserId());
                        return ps;
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return bookDto;
    }

    @Override
    public BookDto updateBook(@NotNull BookDto bookDto) {
        final String UPDATE_SQL = "UPDATE BOOK SET TITLE = ?, AUTHOR = ?, PAGE_COUNT = ? where ID = ?;";

        int count = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
            ps.setString(1, bookDto.getTitle());
            ps.setString(2, bookDto.getAuthor());
            ps.setLong(3, bookDto.getPageCount());
            ps.setLong(4, bookDto.getId());

            return ps;
        });

        if(count == 0){
            throw new NotFoundException("book with this id: " + bookDto.getId() + "not found");
        }

        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        final String SELECT_BY_ID_SQL = "SELECT * FROM BOOK WHERE ID = ?";

        Book book = jdbcTemplate.query(SELECT_BY_ID_SQL,
                ps -> ps.setLong(1, id),
                rse -> {
                    Book res = new Book(
                            rse.getLong("id"),
                            rse.getLong("user_id"),
                            rse.getString("title"),
                            rse.getString("author"),
                            rse.getLong("pageCount")
                    );
                    return res;
                });

        return bookMapper.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        final String DELETE_SQL = "DELETE FROM BOOK WHERE ID = ?";

        jdbcTemplate.update(DELETE_SQL, ps -> {
            ps.setLong(1, id);
        });
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        final String DELETE_BY_USER_ID_SQL = "DELETE FROM BOOK WHERE USER_ID = ?";

        jdbcTemplate.update(DELETE_BY_USER_ID_SQL, ps -> {
            ps.setLong(1, userId);
        });
    }

    @Override
    public List<BookDto> getAllByUserId(Long userId) {
        final String SELECT_BY_USER_ID_SQL = "SELECT * FROM BOOK WHERE USER_ID = ?";

        List<Book> books = jdbcTemplate.query(SELECT_BY_USER_ID_SQL, ROW_MAPPER, userId);

        return books.stream().
                map(bookMapper::bookToBookDto).
                toList();
    }
}
