package com.nineleaps.orderservice.model;

import com.datastax.driver.core.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@Data
@NoArgsConstructor
@UserDefinedType(value = "products")
@ApiModel(description = "All the details related to product")
public class Product implements Serializable {

    private static final long serialVersionUID = -143215566323L;

    @Column(value = "product_id")
    @CassandraType(type = DataType.Name.UUID)
    @NotNull(message = "Product id can not be null")
    @ApiModelProperty(notes = "The product id")
    private UUID productId;

    @Column(value = "quantity")
    @CassandraType(type = DataType.Name.INT)
    @NotNull(message = "Quantity can not be null")
    @Range(min = 1, message = "Quantity can not be 0")
    @ApiModelProperty(notes = "The number quantity")
    private Integer quantity;

    private Product(final UUID productId, final Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private UUID productId;
        private Integer quantity;

        public ProductBuilder addProductId(final UUID productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder addQuantity(final Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Product build() {
            return new Product(productId, quantity);
        }
    }
}
