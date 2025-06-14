package com.spring_kafka.producer;

import static com.spring_kafka.model.Topic.MY_JSON_TOPIC;

import com.spring_kafka.model.MyMessage;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyProducer{

    //spring-kafka에서 제공하는 bean
    private final KafkaTemplate<String, MyMessage> kafkaTemplate;

    public void sendMessage(MyMessage myMessage) {
        kafkaTemplate.send(MY_JSON_TOPIC, String.valueOf(myMessage.getAge()), myMessage);
    }
}
