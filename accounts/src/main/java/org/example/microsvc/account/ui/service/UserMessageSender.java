package org.example.microsvc.account.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserMessageSender {

    public static final String TOPIC = "validateuser";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String key, String value) {
        kafkaTemplate.send(TOPIC, key, value);
    }
}
