package com.example.zoostore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ClothesDtoRequest extends ProductDtoRequest {

    private String size;

    private Long categoryId;
}
