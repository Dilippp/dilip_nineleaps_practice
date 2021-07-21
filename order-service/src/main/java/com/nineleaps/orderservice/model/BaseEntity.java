package com.nineleaps.orderservice.model;

import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;

import java.time.LocalDateTime;

@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public class BaseEntity {

    @JsonIgnore
    @CassandraType(type = DataType.Name.TIMESTAMP)
    @Column(value = "created_date")
    @CreatedDate
    private LocalDateTime createdOn;

    @JsonIgnore
    @CassandraType(type = DataType.Name.VARCHAR)
    @Column(value = "created_by")
    @CreatedBy
    private String createdBy;

    @JsonIgnore
    @CassandraType(type = DataType.Name.TIMESTAMP)
    @Column(value = "updated_date")
    @LastModifiedDate
    private LocalDateTime updatedOn;

    @JsonIgnore
    @CassandraType(type = DataType.Name.VARCHAR)
    @Column(value = "updated_by")
    @LastModifiedBy
    private String updatedBy;

}