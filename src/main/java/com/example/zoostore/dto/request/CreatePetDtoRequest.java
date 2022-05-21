package com.example.zoostore.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreatePetDtoRequest {
    private Long id;

    private Long categoryId;

    private String name;

    private Double price;

    private String description;

    private MultipartFile image;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean gender;

    private String city;

    private String ownerNumber;

    private String breed;
}
