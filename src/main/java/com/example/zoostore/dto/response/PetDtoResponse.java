package com.example.zoostore.dto.response;

import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;

@Data
public class PetDtoResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private File photo;
    private Long categoryId;
    private Long groupId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
