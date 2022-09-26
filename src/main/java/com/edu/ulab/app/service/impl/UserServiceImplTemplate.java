package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Primary
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;

    private final UserMapper userMapper;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        final String UPDATE_SQL = "UPDATE PERSON SET FULL_NAME = ?, TITLE = ?, AGE = ? where ID = ?;";

        int count = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
            ps.setString(1, userDto.getFullName());
            ps.setString(2, userDto.getTitle());
            ps.setLong(3, userDto.getAge());
            ps.setLong(4, userDto.getId());

            return ps;
        });

        if(count == 0){
            throw new NotFoundException("user with this id: " + userDto.getId() + "not found");
        }

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        final String DELETE_SQL = "SELECT * FROM PERSON WHERE ID = ?";

        Person person = jdbcTemplate.query(
                DELETE_SQL,
                ps -> ps.setLong(1, id),
                rse -> {
                    Person res = new Person(
                            rse.getLong("id"),
                            rse.getString("full_name"),
                            rse.getString("title"),
                            rse.getInt("age")
                    );
                    return res;
                });

        return userMapper.personToUserDto(person);
    }

    @Override
    public void deleteUserById(Long id) {
        final String DELETE_SQL = "DELETE FROM PERSON WHERE ID = ?";

        jdbcTemplate.update(DELETE_SQL, ps -> {
            ps.setLong(1, id);
        });
    }

    @Override
    public boolean existsById(Long id) {
        final String SELECT_EXISTS_SQL = "SELECT count(*) FROM PERSON WHERE ID = ?";

        int res = jdbcTemplate.queryForObject(SELECT_EXISTS_SQL, Integer.class, id);

        return res > 0;
    }
}

