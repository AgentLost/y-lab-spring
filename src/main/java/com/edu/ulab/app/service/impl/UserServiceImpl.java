package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dao.UserDAO;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    private final UserMapper userMapper;

    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(@NotNull UserDto userDto) {
        User user = userDAO.createUser(userDto);
        return userMapper.userToUserDto(user);
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
    }

    @Override
    public UserDto updateUser(@NotNull UserDto userDto) {
        User user;
        if(userDAO.existsById(userDto.getId())){
            user = userDAO.updateUser(userDto);
        }else{
            user = userDAO.createUser(userDto);
        }
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        if(!userDAO.existsById(id)){
            throw new NotFoundException("user with this id: " +  id + " not found");
        }

        return userMapper.userToUserDto(
                    userDAO.getUserById(id));
    }

    @Override
    public void deleteUserById(Long id) {
        if(!userDAO.existsById(id)){
            throw new NotFoundException("user with this id: " +  id + " not found");
        }

        userDAO.deleteUserById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userDAO.existsById(id);
    }
}
