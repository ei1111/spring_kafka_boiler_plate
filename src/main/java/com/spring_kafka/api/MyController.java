package com.spring_kafka.api;

import com.spring_kafka.model.MyMessage;
import com.spring_kafka.producer.MyProducer;
import com.spring_kafka.producer.MySecondProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyController {
    private final MyProducer myProducer;
    private final MySecondProducer mySecondProducer;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/message")
    public void message(@RequestBody MyMessage myMessage) {
        //myProducer가 yml의 definition 정의된 myProducer로 가면 producer-test로 가서
        //producer-test.desination인 my-json-topic으로 간다.
        myProducer.sendMessage(myMessage);
    }

    @PostMapping("/second-message/{key}")
    public void message(
            @PathVariable String key,
            @RequestBody String message) {
        mySecondProducer.sendMessageWithKey(key, message);
    }
}
