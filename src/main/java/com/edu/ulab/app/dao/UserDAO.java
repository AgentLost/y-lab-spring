package com.edu.ulab.app.dao;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;

public interface UserDAO {
    User createUser(UserDto userDto);

    User updateUser(UserDto userDto);

    User getUserById(Long id);

    void deleteUserById(Long id);

    boolean existsById(Long id);
}
