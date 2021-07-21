package com.nineleaps.orderservice.service;

import com.nineleaps.orderservice.model.Order;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
public interface OrderService {

    Order getOrderById(UUID orderId);

    Order getOrderByCustomerId(Integer customerId);

    Order saveOrUpdateOrder(Order order);

    void deleteOrder(UUID orderId);
}
