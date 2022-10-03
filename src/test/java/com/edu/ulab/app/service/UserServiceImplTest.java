package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.UserServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    @DisplayName("Save user. Successfully.")
    void savePerson_Test() {
        //given

        UserDto userDto = new UserDto();
        userDto.setAge(11);
        userDto.setFullName("test name");
        userDto.setTitle("test title");

        Person person  = new Person();
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(userMapper.userDtoToPerson(userDto)).thenReturn(person);
        when(userRepository.save(person)).thenReturn(savedPerson);
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);


        //then

        UserDto userDtoResult = userService.createUser(userDto);
        assertEquals(1L, userDtoResult.getId());
    }

    // update
    @Test
    @DisplayName("Update user. Successfully.")
    void updatePerson_Test() {
        //given

        UserDto userUpdateDto = new UserDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setAge(18);
        userUpdateDto.setFullName("test");
        userUpdateDto.setTitle("1");

        Person updatePerson  = new Person();
        updatePerson.setId(1L);
        updatePerson.setFullName("test");
        updatePerson.setAge(18);
        updatePerson.setTitle("1");

        UserDto resultUpdate = new UserDto();
        resultUpdate.setId(1L);
        resultUpdate.setFullName("test");
        resultUpdate.setAge(18);
        resultUpdate.setTitle("1");

        //when

        when(userMapper.userDtoToPerson(userUpdateDto)).thenReturn(updatePerson);
        when(userRepository.findById(updatePerson.getId())).thenReturn(Optional.of(updatePerson));
        when(userRepository.save(updatePerson)).thenReturn(updatePerson);
        when(userMapper.personToUserDto(updatePerson)).thenReturn(resultUpdate);


        //then
        UserDto userDtoResult = userService.updateUser(userUpdateDto);
        assertEquals(1L, userDtoResult.getId());
        assertEquals("test", userDtoResult.getFullName());
        assertEquals("1", userDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Get user. Successfully.")
    void getPerson_Test() {
        //given
        Long userId = 1L;

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test");
        savedPerson.setAge(11);
        savedPerson.setTitle("test");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test");
        result.setTitle("test");


        //when

        when(userRepository.findById(userId)).thenReturn(Optional.of(savedPerson));
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);


        //then

        UserDto userDtoResult = userService.getUserById(userId);
        assertEquals(1L, userDtoResult.getId());
        assertEquals(11, userDtoResult.getAge());
        assertEquals("test", userDtoResult.getFullName());
    }
    // delete
    @Test
    @DisplayName("Delete user. Successfully.")
    void deletePerson_Test() {
        //given

        Long personId = 1L;

        //when
        doNothing().when(userRepository).deleteById(personId);

        //then
        userService.deleteUserById(personId);
    }

    // * failed

    @Test
    @DisplayName("Failed get user. Successfully.")
    void failedGetPerson_Test() {
        //given
        Long userId = 1L;

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test");
        savedPerson.setAge(11);
        savedPerson.setTitle("test");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test");
        result.setTitle("test");


        //when

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);


        //then

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with id: 1 not found");
    }

    @Test
    @DisplayName("Failed update user. Successfully.")
    void failedUpdatePerson_Test() {
        //given

        UserDto userUpdateDto = new UserDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setAge(18);
        userUpdateDto.setFullName("nik");
        userUpdateDto.setTitle("1");

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test");
        savedPerson.setAge(11);
        savedPerson.setTitle("test");

        Person updatePerson  = new Person();
        updatePerson.setId(1L);
        updatePerson.setFullName("nik");
        updatePerson.setAge(18);
        updatePerson.setTitle("1");

        UserDto resultUpdate = new UserDto();
        resultUpdate.setId(1L);
        resultUpdate.setFullName("nik");
        resultUpdate.setAge(18);
        resultUpdate.setTitle("1");

        //when

        when(userMapper.userDtoToPerson(userUpdateDto)).thenReturn(updatePerson);
        when(userRepository.findById(updatePerson.getId())).thenReturn(Optional.empty());
        when(userRepository.save(updatePerson)).thenReturn(updatePerson);
        when(userMapper.personToUserDto(updatePerson)).thenReturn(resultUpdate);


        //then
        assertThatThrownBy(() -> userService.updateUser(userUpdateDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User with id: 1 not found");
    }
}