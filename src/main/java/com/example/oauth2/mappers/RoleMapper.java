package com.example.oauth2.mappers;

import com.example.oauth2.dto.response.RoleDtoResponse;
import com.example.oauth2.model.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RoleMapper {
    RoleDtoResponse toResponse(Role role);
}
