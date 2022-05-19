package com.example.oauth2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddUserDtoRequest {
    private String email;
    private String password;
    private String rePassword;
    private Long roleId;

    public AddUserDtoRequest(String email) {
        this.email = email;
    }
}
