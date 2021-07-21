package com.nineleaps.supplierservice.service;

import com.nineleaps.supplierservice.model.Supplier;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public interface SupplierService {

    Supplier getSupplier(UUID supplierId);

    Supplier saveOrUpdateSupplier(Supplier supplier);

    void deleteSupplier(UUID supplierId);
}
