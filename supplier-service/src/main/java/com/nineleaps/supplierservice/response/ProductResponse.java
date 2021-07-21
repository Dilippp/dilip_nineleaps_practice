package com.nineleaps.supplierservice.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Data
@NoArgsConstructor
public class ProductResponse {
    private UUID productId;
    private String name;
    private Double price;
    private UUID supplierId;
}
