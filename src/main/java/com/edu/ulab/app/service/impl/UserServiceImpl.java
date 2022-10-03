package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(@NotNull UserDto userDto) {
        Person user = userRepository.save(
                userMapper.userDtoToPerson(userDto));
        log.info("Create user: {}", user);
        return userMapper.personToUserDto(user);
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
    }

    @Override
    public UserDto updateUser(@NotNull UserDto userDto) {
        userRepository.findById(userDto.getId())
                .orElseThrow(()->new NotFoundException("user with this id: " +  userDto.getId() + " not found"));
        Person updatePerson = userRepository.save(
                userMapper.userDtoToPerson(userDto));
        log.info("Update user: {}", updatePerson);
        return userMapper.personToUserDto(updatePerson);
    }

    @Override
    public UserDto getUserById(Long id) {
        Person user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user with this id: " +  id + " not found"));
        log.info("Get user: {}", user);
        return userMapper.personToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
