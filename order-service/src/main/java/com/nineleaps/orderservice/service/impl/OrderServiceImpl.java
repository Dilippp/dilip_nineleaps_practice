package com.nineleaps.orderservice.service.impl;

import com.nineleaps.orderservice.callback.CustomKafkaCallback;
import com.nineleaps.orderservice.exception.ResourceNotFoundException;
import com.nineleaps.orderservice.exception.ServiceException;
import com.nineleaps.orderservice.model.Order;
import com.nineleaps.orderservice.repository.OrderRepository;
import com.nineleaps.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Order service.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String topicName;

    /**
     * Instantiates a new Order service.
     *
     * @param orderRepository the order repository
     * @param kafkaTemplate   the kafka template
     * @param topicName       the topic name
     */
    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository,
                            final KafkaTemplate<String, Order> kafkaTemplate,
                            final @Value("${kafka.topic.name}") String topicName) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    /**
     * A method to get order by id
     *
     * @param orderId the order id
     * @return the order
     */
    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).
                orElseThrow(() -> new ResourceNotFoundException("No order found by id: " + orderId));
    }

    @Override
    public Order getOrderByCustomerId(Integer customerId) {
        return orderRepository.getOrderByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("No order found by id: " + customerId));
    }

    /**
     * Method to place order
     *
     * @param order the order
     * @return the order
     * @throws ServiceException the service exception
     */
    @Override
    @SuppressWarnings("all")
    public Order saveOrUpdateOrder(final Order order) {
        try {
            final Order persistOrder = orderRepository.save(order);
            kafkaTemplate.send(topicName, persistOrder).addCallback(new CustomKafkaCallback());
            return persistOrder;
        } catch (Exception ex) {
            log.error("Unable to save or update data: ", ex);
            throw new ServiceException("Error while persisting or updating data");
        }
    }

    /**
     * Method to delete a order
     *
     * @param orderId the order id
     */
    @Override
    public void deleteOrder(UUID orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            orderRepository.deleteById(orderId);
        } else {
            throw new ResourceNotFoundException("No record found to delete for order id: " + orderId);
        }
    }
}
