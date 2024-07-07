package com.orderservice.kafka.consumer;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(
            topics = "createUser",
            groupId = "foo")
    public void greetingListener(Map<String, String> greeting) {
        System.out.println("1111111111111111111111111111111111111");
        System.out.println(greeting);
    }

    @KafkaListener(
            topics = "removeUser",
            groupId = "foo")
    public void greeting(Map<String, String> greeting) {
        System.out.println("222222222");
        System.out.println(greeting);
    }
}
