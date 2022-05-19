package com.example.oauth2.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDtoRequest {
    private String email;
    private String password;
    private String rePassword;
}
