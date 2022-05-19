package com.example.oauth2.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorityDtoResponse implements Serializable {
    private final Long id;
    private final String name;
}
