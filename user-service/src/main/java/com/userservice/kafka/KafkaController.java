package com.userservice.kafka;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.kafka.producer.UserEventProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kafka/test")
@RequiredArgsConstructor
public class KafkaController {

    private final UserEventProducer userEventProducer;

    @PostMapping("create")
    public void sendMessage() {
        userEventProducer.sendUserCreatedEvent(Map.of("id", "1"));
    }

    @PostMapping("remove")
    public void removeMessage() {
        userEventProducer.sendRemovedEvent(Map.of("id", "2"));
    }
}
