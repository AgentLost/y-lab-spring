package com.edu.ulab.app.dao.impl;

import com.edu.ulab.app.dao.UserDAO;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {
    private final UserStorage userStorage;

    private final UserMapper userMapper;

    public UserDAOImpl(UserStorage userStorage, UserMapper userMapper) {
        this.userStorage = userStorage;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserDto userDto) {
        User user = userStorage.createUser(
                userMapper.userDtoToUser(userDto));
        System.out.println(user);
        return user;
    }

    @Override
    public User updateUser(UserDto userDto) {
        return userStorage.updateUser(
                userMapper.userDtoToUser(userDto));
    }

    @Override
    @Nullable
    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userStorage.deleteUserById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userStorage.existsById(id);
    }
}
