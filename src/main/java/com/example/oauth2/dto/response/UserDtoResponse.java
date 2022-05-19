package com.example.oauth2.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDtoResponse implements Serializable {
    private final Long id;
    private final String email;
    private final Boolean registrationConfirmed;
    private final AuthProviderDtoResponse authProvider;
    private final LocalDateTime createdTime;
    private final RoleDto role;
}
