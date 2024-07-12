package com.userservice.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.userservice.dto.CreateUserDto;
import com.userservice.dto.RegisterUserDto;
import com.userservice.dto.UserDto;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.eventprocessing.service.UserEventService;
import com.userservice.exception.EmailAlreadyInUseException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserEventService userEventService;

    @InjectMocks
    private UserService userService;

    @Test
    void testUpdateUser() {
        UserDto userDto = new UserDto(1L, "name", "email@email.com", Role.USER);
        User user = new User();
        user.setEmail("email@email.com");
        //Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        UserDto res = userService.updateUser(userDto);
        assertNotNull(res);
        assertEquals(res, userDto);
    }

    @Test
    void testUpdateUserValidEmail() {
        UserDto userDto = new UserDto(1L, "name", "email@email.com", Role.USER);
        User user = new User();
        user.setEmail("email2@email.com");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        UserDto res = userService.updateUser(userDto);
        assertNotNull(res);
        assertEquals(res, userDto);
    }

    @Test
    void testUpdateUserInValidEmail() {
        UserDto userDto = new UserDto(1L, "name", "email@email.com", Role.USER);
        User user = new User();
        user.setEmail("email2@email.com");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new User()));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        assertThrows(EmailAlreadyInUseException.class, () -> userService.updateUser(userDto));
    }

    @Test
    void testUpdateUserUserNotFound() {
        UserDto userDto = new UserDto(1L, "name", "email@email.com", Role.USER);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto));
    }

    @Test
    void testCreateUser() {
        CreateUserDto createUserDto = new CreateUserDto("name", "email@email.com", "123", Role.USER);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("123");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User());
        Mockito.doNothing().when(userEventService).sendUserCreatedEvent(Mockito.any());
        UserDto user = userService.createUser(createUserDto);
        assertNotNull(user);
    }

    @Test
    void testRegisterUser() {
        RegisterUserDto registerUserDto = new RegisterUserDto("name", "email@email.com", "123");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("123");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User());
        Mockito.doNothing().when(userEventService).sendUserCreatedEvent(Mockito.any());
        User user = userService.registerUser(registerUserDto);
        assertNotNull(user);
    }

    @Test
    void testCreateUserEmailIsUsed() {
        CreateUserDto createUserDto = new CreateUserDto("name", "email@email.com", "123", Role.USER);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new User()));
        assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(createUserDto));
    }

    @Test
    void testRegisterUserEmailIsUsed() {
        RegisterUserDto registerUserDto = new RegisterUserDto("name", "email@email.com", "123");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new User()));
        assertThrows(EmailAlreadyInUseException.class, () -> userService.registerUser(registerUserDto));
    }

    @Test
    void testRemoveUserById() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new User()));
        Mockito.doNothing().when(userEventService).sendUserRemovedEvent(Mockito.any());
        userService.removeUserById(1L);
    }

    @Test
    void testRemoveUserByIdUserNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.removeUserById(1L));
    }
}