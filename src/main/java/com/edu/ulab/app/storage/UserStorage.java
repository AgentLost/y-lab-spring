package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(Long id);

    void deleteUserById(Long id);

    boolean existsById(Long id);
}
