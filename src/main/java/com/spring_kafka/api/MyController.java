package com.spring_kafka.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring_kafka.model.MyMessage;
import com.spring_kafka.producer.MyProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {
    private final MyProducer myProducer;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/message")
    public void message(@RequestBody MyMessage myMessage) {
        try {
            myProducer.sendMessage(myMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
