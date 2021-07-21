package com.nineleaps.orderservice.repository;

import com.nineleaps.orderservice.model.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Order repository.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
public interface OrderRepository extends CassandraRepository<Order, UUID> {

    @Query("SELECT * FROM orders WHERE customer_id=?0")
    Optional<Order> getOrderByCustomerId(Integer customerId);
}
