package com.nineleaps.supplierservice.model;

import com.datastax.driver.core.DataType;
import com.nineleaps.supplierservice.utilities.AppConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Data
@NoArgsConstructor
@Table(value = "supplier")
public class Supplier {

    @PrimaryKey(value = "supplier_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID supplierId = UUID.randomUUID();

    @Column(value = "supplier_name")
    @CassandraType(type = DataType.Name.VARCHAR)
    @NotBlank(message = "Supplier name can not be null or empty")
    private String name;

    @Column(value = "supplier_email")
    @CassandraType(type = DataType.Name.VARCHAR)
    @NotBlank(message = "Supplier email must not be empty")
    @Email(regexp = AppConstant.EMAIL_PATTERN, message = "Email is invalid")
    private String email;
}
