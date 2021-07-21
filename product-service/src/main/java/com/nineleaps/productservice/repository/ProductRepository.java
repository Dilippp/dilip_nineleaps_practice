package com.nineleaps.productservice.repository;

import com.nineleaps.productservice.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public interface ProductRepository extends CassandraRepository<Product, UUID> {
}
