package com.userservice.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userservice.dto.RegisterUserDto;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.exception.EmailAlreadyInUseException;
import com.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User addUser(RegisterUserDto registerUserDto) {
        log.info("Adding user with email: {}", registerUserDto.email());
        checkIfUserExists(registerUserDto.email());
        User user = new User();
        BeanUtils.copyProperties(registerUserDto, user, "password");
        user.setPassword(passwordEncoder.encode(registerUserDto.password()));
        user.setRole(Role.USER);
        User result = userRepository.save(user);
        log.info("User added with id: {}", result.getId());
        return result;
    }

    public User getUserByEmail(String email) {
        return  findUserByEmail(email).orElseThrow();
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
