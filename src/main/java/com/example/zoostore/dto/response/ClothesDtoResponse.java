package com.example.zoostore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ClothesDtoResponse {
    private Long id;
    private Long productId;

    private String size;

    private Long categoryId;

    private String name;

    private Double price;

    private String description;

    private Integer quantity;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}
