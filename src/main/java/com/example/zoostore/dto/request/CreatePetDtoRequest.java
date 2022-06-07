package com.example.zoostore.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreatePetDtoRequest extends ProductDtoRequest {
    private Long id;

    private Long categoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean gender;

    private String city;

    private String ownerNumber;

    private MultipartFile image;


    private String breed;
}
