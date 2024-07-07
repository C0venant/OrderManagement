package com.userservice.kafka.producer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.userservice.kafka.event.UserEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreatedEventProducer {

    private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @Value("${message.topic.create-user}")
    private String topic;

    @Value("${message.topic.remove-user}")
    private String topic2;

    public void sendMessage(Map<String, String> userEvent) {
        kafkaTemplate.send(topic, userEvent);
    }

    public void removeMessage(Map<String, String> userEvent) {
        kafkaTemplate.send(topic2, userEvent);
    }
}
