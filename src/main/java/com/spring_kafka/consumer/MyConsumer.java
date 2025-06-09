package com.spring_kafka.consumer;


import static com.spring_kafka.model.Topic.MY_JSON_TOPIC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_kafka.model.MyMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Synchronized;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Integer> idHistory = new ConcurrentHashMap<>();

    @KafkaListener(
            topics = {MY_JSON_TOPIC},
            groupId = "test-consumer-group",
            concurrency = "1"
    )
    public void accept(ConsumerRecord<String, String> message, Acknowledgment acknowledgment)
            throws JsonProcessingException {
        MyMessage myMessage = objectMapper.readValue(message.value(), MyMessage.class);
        printPayloadIfFirstMessage(myMessage);
        //수동 커밋과정
        acknowledgment.acknowledge();
    }

    // 프린트를 중복이 아닌 첫번째 메시지만 프린트 찍어준다.
    private void printPayloadIfFirstMessage(MyMessage myMessage) {
        if (idHistory.putIfAbsent(String.valueOf(myMessage.getId()), 1) == null) {
            //Exactly One 실행되어야 하는 로직
            System.out.println("[main Consumer]message arrived! = " + myMessage);
        }else {
            System.out.println("[main Consumer]message Deplicate! = " + myMessage.getId());
        }
    }
}
