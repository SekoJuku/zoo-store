package com.example.zoostore.service;

import com.example.oauth2.model.User;
import com.example.oauth2.security.JWTTokenProvider;
import com.example.oauth2.service.UserService;
import com.example.oauth2.util.HttpUtils;
import com.example.zoostore.dto.request.ProfileDetailsDtoRequest;
import com.example.zoostore.utils.model.CustomerFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    public User edit(ProfileDetailsDtoRequest request) {
        Long userId = getUserId();
        User user = userService.getUserById(userId);
        CustomerFacade.CustomerDtoToUser(request,user);
        return userService.save(user);
    }

    public User getById(Long id) {
        verifyId(id);
        return userService.getUserById(id);
    }

    public User getById() {
        return getById(getUserId());
    }

    private void verifyId(Long id) {
        Long userId = getUserId();
        User userById = userService.getUserById(userId);
        if (Objects.isNull(userById) || !userById.getId().equals(id)) {
            throw new IllegalArgumentException("User can edit only his profile");
        }
    }

    public Long getUserId() {
        String jwt = HttpUtils.getJWT();
        Long userId = Long.valueOf(jwtTokenProvider.getSubject(jwt));
        return userId;
    }
}
