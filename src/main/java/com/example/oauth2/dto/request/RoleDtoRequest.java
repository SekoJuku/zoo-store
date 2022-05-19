package com.example.oauth2.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RoleDtoRequest implements Serializable {
    private final Long id;
    private final String name;
    private final Set<AuthorityDtoRequest> authorities;
}
