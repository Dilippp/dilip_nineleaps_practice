package com.nineleaps.supplierservice.repository;

import com.nineleaps.supplierservice.model.Supplier;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
public interface SupplierRepository extends CassandraRepository<Supplier, UUID> {
}
