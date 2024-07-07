package com.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userservice.dto.CreateUserDto;
import com.userservice.dto.RegisterUserDto;
import com.userservice.dto.UserDto;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.exception.EmailAlreadyInUseException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return UserDto.of(findUserById(id));
    }

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        log.info("Creating user: {}", createUserDto);
        checkIfUserExists(createUserDto.email());
        User user = new User();
        BeanUtils.copyProperties(createUserDto, user, "password");
        user.setPassword(passwordEncoder.encode(createUserDto.password()));
        return UserDto.of(addUser(user));
    }

    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = findUserById(id);
        userRepository.delete(user);
        log.info("Deleted user with id: {}", id);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        log.info("Updating user: {}", userDto);
        User user = findUserById(userDto.id());
        BeanUtils.copyProperties(userDto, user);
        return userDto;
    }

    @Transactional
    public User registerUser(RegisterUserDto registerUserDto) {
        log.info("Registering user with email: {}", registerUserDto.email());
        checkIfUserExists(registerUserDto.email());
        User user = new User();
        BeanUtils.copyProperties(registerUserDto, user, "password");
        user.setPassword(passwordEncoder.encode(registerUserDto.password()));
        user.setRole(Role.USER);
        return addUser(user);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return  findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new UserNotFoundException(id);
                });
    }

    private User addUser(User user) {
        User result = userRepository.save(user);
        log.info("User added with id: {}", result.getId());
        return result;
    }

    private void checkIfUserExists(String email) {
        findUserByEmail(email)
                .ifPresent(user -> {
                    log.error("User already exists with email {}", email);
                    throw new EmailAlreadyInUseException();
                });
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
