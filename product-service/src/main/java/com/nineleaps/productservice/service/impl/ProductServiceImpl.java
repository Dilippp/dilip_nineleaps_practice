package com.nineleaps.productservice.service.impl;

import com.nineleaps.productservice.exception.ResourceNotFoundException;
import com.nineleaps.productservice.exception.ServiceException;
import com.nineleaps.productservice.model.Product;
import com.nineleaps.productservice.repository.ProductRepository;
import com.nineleaps.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("No product found by id: " + productId));
    }

    @Override
    public Product saveOrUpdateProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception ex) {
            log.error("Unable to save or update data: ", ex);
            throw new ServiceException("Error while persisting or updating data");
        }
    }

    @Override
    public void deleteProduct(UUID productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.deleteById(productId);
        } else {
            throw new ResourceNotFoundException("No record found to delete for product id: " + productId);
        }
    }
}
