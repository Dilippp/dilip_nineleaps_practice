package com.nineleaps.orderservice.config;

import com.nineleaps.orderservice.model.Order;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Kafka producer config.
 *
 * @author Dilip Chauhan Created on 22/03/2020
 */
@Configuration
@NoArgsConstructor
public class KafkaProducerConfig {

    /**
     * Field to map kafka server IP
     */
    @Value("${kafka.server.url}")
    private String kafkaServer;

    /**
     * A method to create producer factory.
     *
     * @return the producer factory
     */
    @Bean
    public ProducerFactory<String, Order> producerFactory() {
        final Map<String, Object> config = new ConcurrentHashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 25);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Kafka template by using producer factory.
     *
     * @return the kafka template
     */
    @Bean
    public KafkaTemplate<String, Order> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
