package com.spring_kafka.producer;

import static com.spring_kafka.model.Topic.MY_JSON_TOPIC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_kafka.model.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyProducer{

    ObjectMapper objectMapper = new ObjectMapper();
    //spring-kafka에서 제공하는 bean
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(MyMessage myMessage) throws JsonProcessingException {
        kafkaTemplate.send(
                MY_JSON_TOPIC
                , String.valueOf(myMessage.getAge())
                , objectMapper.writeValueAsString(myMessage)
        );
    }
}
