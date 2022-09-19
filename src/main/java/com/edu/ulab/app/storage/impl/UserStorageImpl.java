package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorageImpl implements UserStorage {
    private final Long START_ID_VALUE = -1L;

    private long CURRENT_ID_VALUE;

    private final Map<Long, User> USER_DATA = new HashMap<>();

    @PostConstruct
    public void init(){
        CURRENT_ID_VALUE = START_ID_VALUE;
    }

    public void grow(){
        CURRENT_ID_VALUE++;
    }


    @Override
    public User createUser(@NotNull User user) {
        grow();
        user.setId(CURRENT_ID_VALUE);
        USER_DATA.put(CURRENT_ID_VALUE, user);
        return USER_DATA.get(CURRENT_ID_VALUE);
    }

    @Override
    public User updateUser(@NotNull User user) {
        return USER_DATA.put(user.getId(), user);
    }

    @Override
    @Nullable
    public User getUserById(Long id) {
        return USER_DATA.get(id);
    }

    @Override
    public void deleteUserById(Long id) {
        if(USER_DATA.remove(id) == null){
            throw new NotFoundException("user with this id: " +  id + " not found");
        }
    }

    @Override
    public boolean existsById(Long id) {
        return USER_DATA.containsKey(id);
    }
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции
}
