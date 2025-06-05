package com.spring_kafka.producer;

import static com.spring_kafka.model.Topic.MY_JSON_TOPIC;
import static com.spring_kafka.model.Topic.MY_SECOND_TOPIC;

import com.spring_kafka.model.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MySecondProducer {

    //spring-kafka에서 제공하는 bean
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageWithKey(String key,String myMessage) {
        kafkaTemplate.send(MY_SECOND_TOPIC, myMessage);
    }
}
