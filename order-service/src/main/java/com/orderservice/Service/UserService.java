package com.orderservice.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orderservice.entity.User;
import com.orderservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void addUser(Map<String, String> userData) {
        userRepository.save(getUser(userData));
    }

    public void removeUser(Map<String, String> userData) {
        userRepository.delete(getUser(userData));
    }

    public boolean isUserPresent(Long userId) {
        return userRepository.existsById(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    private User getUser(Map<String, String> userData) {
        return new User(Long.valueOf(userData.get("id")));
    }
}
