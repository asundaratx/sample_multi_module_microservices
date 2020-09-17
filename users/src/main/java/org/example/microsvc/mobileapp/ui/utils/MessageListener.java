package org.example.microsvc.mobileapp.ui.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.microsvc.mobileapp.ui.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {
    private UserServiceInterface userServiceInterface;
    private static final String TOPIC ="validateuser";

    @Autowired
    MessageListener(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    @KafkaListener(topics = TOPIC)
    public void handle(ConsumerRecord<String, String> cr) {
        String userId = cr.value();
        log.info("Message: "+cr.key()+"="+userId);
        userServiceInterface.validateUserByUserId(userId);
    }
}
