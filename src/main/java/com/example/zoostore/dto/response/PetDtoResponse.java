package com.example.zoostore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PetDtoResponse {
    private Long id;
    private Long productId;
    private Long categoryId;
    private String name;
    private String image;
    private Double price;
    private String description;
    private Boolean gender;
    private String city;
    private String ownerNumber;
    private String breed;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
