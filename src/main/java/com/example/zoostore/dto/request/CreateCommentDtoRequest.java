package com.example.zoostore.dto.request;

import lombok.Data;

@Data
public class CreateCommentDtoRequest {
    private Long id;
    private Double star;
    private String text;
}
