package com.nineleaps.productservice.service;

import com.nineleaps.productservice.model.Product;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public interface ProductService {

    Product getProductById(UUID productId);

    Product saveOrUpdateProduct(Product product);

    void deleteProduct(UUID productId);
}
