package com.nineleaps.productservice.model;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Data
@NoArgsConstructor
@Table(value = "product")
public class Product {

    @PrimaryKey(value = "product_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID productId = UUID.randomUUID();

    @NotBlank(message = "Product name must not be null or empty")
    @CassandraType(type = DataType.Name.VARCHAR)
    @Column(value = "product_name")
    private String name;

    @Column(value = "product_price")
    @NotNull(message = "Product price can not be null")
    @Range(min = 1, message = "Product price can not be 0")
    @CassandraType(type = DataType.Name.DOUBLE)
    private Double price;

    @Column(value = "supplier_id")
    @NotNull(message = "Supplier id can not be null")
    @CassandraType(type = DataType.Name.UUID)
    private UUID supplierId;
}
