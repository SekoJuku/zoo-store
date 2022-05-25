package com.example.zoostore.dto.response;

import com.example.zoostore.model.Category;
import com.example.zoostore.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClothesDtoResponse {
    private Long id;

    private String size;

    private Long categoryId;

    private String name;

    private Double price;

    private String description;

    private Integer quantity;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}
