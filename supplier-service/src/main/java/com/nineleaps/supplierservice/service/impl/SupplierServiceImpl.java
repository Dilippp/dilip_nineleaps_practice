package com.nineleaps.supplierservice.service.impl;

import com.nineleaps.supplierservice.exception.ResourceNotFoundException;
import com.nineleaps.supplierservice.exception.ServiceException;
import com.nineleaps.supplierservice.model.Supplier;
import com.nineleaps.supplierservice.repository.SupplierRepository;
import com.nineleaps.supplierservice.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier getSupplier(UUID supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(() ->
                new ResourceNotFoundException("Supplier not found in the database with id: " + supplierId));
    }

    @Override
    public Supplier saveOrUpdateSupplier(Supplier supplier) {
        try {
            return supplierRepository.save(supplier);
        } catch (Exception ex) {
            log.error("Unable to save or update data: ", ex);
            throw new ServiceException("Error while persisting or updating data");
        }
    }

    @Override
    public void deleteSupplier(UUID supplierId) {
        if (supplierRepository.findById(supplierId).isPresent()) {
            supplierRepository.deleteById(supplierId);
            return;
        }
        throw new ResourceNotFoundException("No record found to delete for supplier id: " + supplierId);
    }
}
