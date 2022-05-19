package com.example.oauth2.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorityDtoRequest implements Serializable {
    private final Long id;
    private final String name;
}
