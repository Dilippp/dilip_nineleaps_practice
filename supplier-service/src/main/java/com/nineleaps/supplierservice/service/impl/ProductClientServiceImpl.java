package com.nineleaps.supplierservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.nineleaps.supplierservice.email.EmailService;
import com.nineleaps.supplierservice.model.Email;
import com.nineleaps.supplierservice.model.Order;
import com.nineleaps.supplierservice.model.Product;
import com.nineleaps.supplierservice.model.Supplier;
import com.nineleaps.supplierservice.response.ProductResponse;
import com.nineleaps.supplierservice.service.ProductClientService;
import com.nineleaps.supplierservice.service.SupplierService;
import com.nineleaps.supplierservice.utilities.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Set;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Slf4j
@Service
public class ProductClientServiceImpl implements ProductClientService {

    private final RestTemplate restTemplate;
    private final EmailService emailService;
    private final SupplierService supplierService;
    private final String product_service_url;

    public ProductClientServiceImpl(RestTemplate restTemplate,
                                    EmailService emailService,
                                    SupplierService supplierService,
                                    @Value("${product.service.url}")
                                            String product_service_url) {
        this.restTemplate = restTemplate;
        this.emailService = emailService;
        this.supplierService = supplierService;
        this.product_service_url = product_service_url;
    }

    @Override
    @HystrixCommand(groupKey = "fallback",
            commandKey = "fallback",
            fallbackMethod = "getProductFallback")
    public void getProduct(String message) {

        Order order = JsonUtils.deserialize(message, Order.class);
        Set<Product> products = order.getProducts();
        log.info("Calling product service to get supplier information");
        Email.EmailBuilder emailBuilder = Email.builder().subject("Product information");
        for (Product product : products) {
            ProductResponse productResponse = restTemplate.getForObject(product_service_url + product.getProductId(), ProductResponse.class);
            if (Objects.nonNull(productResponse)) {
                Supplier supplier = supplierService.getSupplier(productResponse.getSupplierId());
                emailBuilder.to(supplier.getEmail()).text(String.format("The supplier id is: %s of product id: %s", productResponse.getSupplierId().toString(), product.getProductId().toString()));
                emailService.sendSimpleMessage(emailBuilder.build());
            }
        }
    }

    public void getProductFallback(String product, Throwable e) {
        log.error("Probably product service is down, please try again!!!" + e.getMessage(), e);
    }
}
