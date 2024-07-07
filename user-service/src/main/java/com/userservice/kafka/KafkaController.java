package com.userservice.kafka;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.kafka.event.UserEvent;
import com.userservice.kafka.producer.UserCreatedEventProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kafka/test")
@RequiredArgsConstructor
public class KafkaController {

    private final UserCreatedEventProducer userCreatedEventProducer;

    @PostMapping("create")
    public void sendMessage() {
        userCreatedEventProducer.sendMessage(Map.of("id", "1"));
    }

    @PostMapping("remove")
    public void removeMessage() {
        userCreatedEventProducer.removeMessage(Map.of("id", "2"));
    }
}
