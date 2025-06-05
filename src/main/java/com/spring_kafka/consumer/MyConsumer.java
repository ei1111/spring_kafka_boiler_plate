package com.spring_kafka.consumer;


import static com.spring_kafka.model.Topic.MY_JSON_TOPIC;

import com.spring_kafka.model.MyMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer{
    @KafkaListener(
            topics = {MY_JSON_TOPIC},
            groupId = "test-consumer-group"
    )
    public void accept(ConsumerRecord<String, MyMessage> message) {
        System.out.println("[main Consumer]message arrived! = " + message.value());
    }
}
