package com.example.zoostore.dto.response;

import com.example.zoostore.model.Category;
import com.example.zoostore.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PetDtoResponse {
    private Long id;
    private Long productId;
    private Long categoryId;
    private String name;
    private Double price;
    private String description;
    private Boolean gender;
    private String city;
    private String ownerNumber;
    private String breed;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
