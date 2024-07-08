package com.userservice.kafka.producer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @Value("${message.topic.create-user}")
    private String createUserTopic;

    @Value("${message.topic.remove-user}")
    private String removeUserTopic;

    public void sendUserCreatedEvent(Map<String, String> userEvent) {
        log.info("Send user created event: {}", userEvent);
        kafkaTemplate.send(createUserTopic, userEvent);
    }

    public void sendRemovedEvent(Map<String, String> userEvent) {
        log.info("Send user removed event: {}", userEvent);
        kafkaTemplate.send(removeUserTopic, userEvent);
    }
}
