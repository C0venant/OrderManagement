package com.userservice.kafka.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.userservice.entity.User;
import com.userservice.kafka.producer.UserEventProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEventService {

    private final UserEventProducer userEventProducer;

    public void sendUserCreatedEvent(User user) {
        userEventProducer.sendUserCreatedEvent(Map.of("id", String.valueOf(user.getId())));
    }

    public void sendUserRemovedEvent(User user) {
        userEventProducer.sendRemovedEvent(Map.of("id", String.valueOf(user.getId())));
    }
}
