package com.nineleaps.supplierservice.listener;

import com.nineleaps.supplierservice.service.ProductClientService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Service
public class KafkaConsumerListener {

    private final ProductClientService productClientService;

    public KafkaConsumerListener(ProductClientService productClientService) {
        this.productClientService = productClientService;
    }

    @KafkaListener(topics = "#{'${kafka.topic.name}'}", groupId = "group_id")
    public void consume(String message) {
        productClientService.getProduct(message);
    }
}
