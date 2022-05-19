package com.example.oauth2.dto.request;

import lombok.Data;

@Data
public class UpdateUserRoleDtoRequest {
    private Long userId;
    private Long roleId;
}
