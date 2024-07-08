package com.userservice.kafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TopicConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${message.topic.create-user}")
    private String createUserTopic;

    @Value("${message.topic.remove-user}")
    private String removeUserTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createUserTopic() {
        return new NewTopic(createUserTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic removeUserTopic() {
        return new NewTopic(removeUserTopic, 1, (short) 1);
    }
}
