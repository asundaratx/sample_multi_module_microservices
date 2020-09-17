package org.example.microsvc.mobileapp.ui.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    public static final String TOPIC ="user";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String key, String value) {
        kafkaTemplate.send(TOPIC, key, value);
    }
}
