package com.spring_kafka.consumer;


import static com.spring_kafka.model.Topic.MY_SECOND_TOPIC;

import com.spring_kafka.model.MyMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MySecondConsumer {
    @KafkaListener(
            topics = {MY_SECOND_TOPIC},
            groupId = "test-consumer-group",
            containerFactory = "secondKafkaListenerContainerFactory"
    )
    public void accept(ConsumerRecord<String, String> message) {
        System.out.println("[second Consumer] message arrived! = " + message.value());
        System.out.println("[second Consumer] Offset = " + message.offset() + "/ Partition = " + message.partition());
    }
}
