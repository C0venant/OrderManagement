package com.orderservice.eventprocessing.consumer;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.orderservice.Service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserService userService;

    @KafkaListener(
            topics = "${message.topic.create-user}",
            groupId = "${message.group}")
    public void createUserListener(Map<String, String> userEvent) {
        log.info("Received user created event: {}", userEvent);
        userService.addUser(userEvent);
    }

    @KafkaListener(
            topics = "${message.topic.remove-user}",
            groupId = "${message.group}")
    public void removeUserListener(Map<String, String> userEvent) {
        log.info("Received user removed event: {}", userEvent);
        userService.removeUser(userEvent);
    }
}
