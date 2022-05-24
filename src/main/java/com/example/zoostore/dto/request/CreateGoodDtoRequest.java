package com.example.zoostore.dto.request;

import lombok.Data;

@Data
public class CreateGoodDtoRequest {

    private Long categoryId;

    private String name;

    private Double price;

    private String description;

    private Integer quantity;
}
