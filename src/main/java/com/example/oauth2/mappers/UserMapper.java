package com.example.oauth2.mappers;

import com.example.oauth2.dto.response.UserDtoResponse;
import com.example.oauth2.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    UserDtoResponse toResponse(User user);
}
