package com.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userservice.dto.CreateUserDto;
import com.userservice.dto.RegisterUserDto;
import com.userservice.dto.UpdateUserDto;
import com.userservice.dto.UserDto;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.exception.EmailAlreadyInUseException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.eventprocessing.service.UserEventService;
import com.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserEventService userEventService;

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
    public UserDto updateUser(UpdateUserDto updateUserDto) {
        String email = getCurrentUserEmail();
        User user = getUserByEmail(email);
        if (isNewEmail(user.getEmail(), updateUserDto.email())) {
            checkIfUserExists(updateUserDto.email()); //check if new email is in use
        }
        BeanUtils.copyProperties(updateUserDto, user);
        return UserDto.of(user);
    }

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        log.info("Create user: {}", createUserDto);
        checkIfUserExists(createUserDto.email());
        User user = new User();
        BeanUtils.copyProperties(createUserDto, user, "password");
        user.setPassword(passwordEncoder.encode(createUserDto.password()));
        return UserDto.of(addUser(user));
    }

    @Transactional
    public void removeUserById(Long id) {
        log.info("Remove user with id: {}", id);
        User user = findUserById(id);
        userRepository.delete(user);
        userEventService.sendUserRemovedEvent(user);
        log.info("Removed user with id: {}", id);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        log.info("Update user: {}", userDto);
        User user = findUserById(userDto.id());
        if (isNewEmail(user.getEmail(), userDto.email())) {
            checkIfUserExists(userDto.email()); //check if new email is in use
        }
        BeanUtils.copyProperties(userDto, user);
        return userDto;
    }

    @Transactional
    public User registerUser(RegisterUserDto registerUserDto) {
        log.info("Register user with email: {}", registerUserDto.email());
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

    private boolean isNewEmail(String email, String newEmail) {
        return !email.equalsIgnoreCase(newEmail);
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
        userEventService.sendUserCreatedEvent(result);
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
