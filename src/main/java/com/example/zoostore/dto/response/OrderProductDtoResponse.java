package com.example.zoostore.dto.response;

import com.example.zoostore.model.Order;
import com.example.zoostore.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
@Builder
public class OrderProductDtoResponse {
    private Long id;

    private Product product;

    private Integer quantity;
}
