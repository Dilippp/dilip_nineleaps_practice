package com.nineleaps.orderservice.model;

import com.datastax.driver.core.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@Data
@NoArgsConstructor
@Table(value = "orders")
@ApiModel(description = "All the details related to order")
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1595075957589L;

    @PrimaryKey(value = "order_id")
    @CassandraType(type = DataType.Name.UUID)
    @ApiModelProperty(notes = "The order id", hidden = true)
    private UUID orderId = UUID.randomUUID();

    @NotNull(message = "Customer id must not be null")
    @Range(min = 1, message = "Customer id can not be 0")
    @CassandraType(type = DataType.Name.INT)
    @Column(value = "customer_id")
    @ApiModelProperty(notes = "Customer id of order")
    private Integer customerId;

    @NotBlank(message = "Customer name is mandatory")
    @CassandraType(type = DataType.Name.VARCHAR)
    @Column(value = "customer_name")
    @ApiModelProperty(notes = "Customer name in order")
    private String customerName;

    @CassandraType(type = DataType.Name.UDT, userTypeName = "products")
    @NotNull(message = "Products can not be null")
    @Valid
    @ApiModelProperty(notes = "Associated products in order")
    private List<Product> products;

    @CassandraType(type = DataType.Name.VARCHAR)
    @NotBlank(message = "Shipping address must not null or empty")
    @Column(value = "shipping_address")
    @ApiModelProperty(notes = "Shipping address of customer")
    private String shippingAddress;
}
