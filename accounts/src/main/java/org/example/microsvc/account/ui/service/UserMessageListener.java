package org.example.microsvc.account.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMessageListener {

    public static final String TOPIC = "user";

    @KafkaListener(topics = TOPIC)
    public void handle(ConsumerRecord<?, ?> cr) {
        log.info("Message: "+cr.key()+"="+cr.value());
    }
}
