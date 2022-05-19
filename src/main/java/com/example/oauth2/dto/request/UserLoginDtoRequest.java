package com.example.oauth2.dto.request;

import lombok.Data;

@Data
public class UserLoginDtoRequest {
    private String email;
    private String password;
}
