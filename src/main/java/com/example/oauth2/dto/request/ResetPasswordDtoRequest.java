package com.example.oauth2.dto.request;

import lombok.Data;

@Data
public class ResetPasswordDtoRequest {
    private String email;
    private String password;
    private String rePassword;
    private Long code;
}
