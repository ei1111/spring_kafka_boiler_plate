package com.spring_kafka.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class SecondKafkaConfig {

    //yml의 spring.kafka 설정 지정
    @Bean
    @Qualifier("secondKafkaProperties")
    @ConfigurationProperties("spring.kafka.string")
    public KafkaProperties secondKafkaProperties() {
        return new KafkaProperties();
    }

    @Bean
    @Qualifier("secondConsumerFactory")
    public ConsumerFactory<String, Object> secondConsumerFactory(KafkaProperties secondConsumerFactory) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, secondConsumerFactory.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                secondConsumerFactory.getConsumer().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                secondConsumerFactory.getConsumer().getValueDeserializer());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @Qualifier("secondKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> secondKafkaListenerContainerFactory(
            ConsumerFactory<String, Object> secondConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(secondConsumerFactory);
        factory.setConcurrency(1);
        return factory;
    }

    @Bean
    @Qualifier("secondProducerFactory")
    public ProducerFactory<String, Object> secondProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaProperties.getProducer().getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaProperties.getProducer().getValueSerializer());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Qualifier("secondKafkaTemplate")
    public KafkaTemplate<String, ?> secondKafkaTemplate(KafkaProperties secondKafkaTemplate) {
        return new KafkaTemplate<>(secondProducerFactory(secondKafkaTemplate));
    }

}
