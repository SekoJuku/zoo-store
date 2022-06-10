package com.example.zoostore.dto.request;

import lombok.Data;

@Data
public class ProductDtoRequest {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String image;
}
