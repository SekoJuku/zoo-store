package com.example.zoostore.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateGoodDtoRequest extends ProductDtoRequest {

    private Long categoryId;

    private MultipartFile image;
}
