package com.example.oauth2.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RoleDtoResponse implements Serializable {
    private final Long id;
    private final String name;
    private final Set<AuthorityDtoResponse> authorities;
}
