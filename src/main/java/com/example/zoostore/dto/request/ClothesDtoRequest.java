package com.example.zoostore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ClothesDtoRequest {
    private MultipartFile image;

    private String size;

    private Long categoryId;

    private String name;

    private Double price;

    private String description;

    private Integer quantity;
}
