package com.nineleaps.supplierservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Data
@NoArgsConstructor
public class Order {
    private UUID orderId;
    private Integer customerId;
    private String customerName;
    private Set<Product> products;
    private String shippingAddress;
}
