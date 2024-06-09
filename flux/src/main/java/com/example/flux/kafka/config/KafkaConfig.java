package com.example.flux.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "51.107.12.112:80");
        return new KafkaAdmin(config);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "51.107.12.112:80");
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.BUFFER_MEMORY_CONFIG,
                104857600
        );
        configProps.put(
                ProducerConfig.MAX_REQUEST_SIZE_CONFIG,
                104857600
        );
        configProps.put(
                "message.max.bytes",
                104857600
        );
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, List<byte[]>> producerFactoryByte() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "51.107.12.112:80");
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                ListOfByteArraySerializer.class);
        configProps.put(
                ProducerConfig.BUFFER_MEMORY_CONFIG,
                104857600
        );
        configProps.put(
                ProducerConfig.MAX_REQUEST_SIZE_CONFIG,
                104857600
        );
        configProps.put(
                "message.max.bytes",
                104857600
        );
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, List<byte[]>> kafkaTemplateByte() {
        return new KafkaTemplate<>(producerFactoryByte());
    }


}
