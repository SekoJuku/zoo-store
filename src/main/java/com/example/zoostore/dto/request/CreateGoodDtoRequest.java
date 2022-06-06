package com.example.zoostore.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateGoodDtoRequest {

    private Long categoryId;

    private String name;

    private Double price;

    private String description;

    private Integer quantity;

    private MultipartFile image;
}
