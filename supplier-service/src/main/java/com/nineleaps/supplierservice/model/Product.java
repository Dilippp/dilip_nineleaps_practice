package com.nineleaps.supplierservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Data
@NoArgsConstructor
public class Product {
    private UUID productId;
    private Integer quantity;
}
