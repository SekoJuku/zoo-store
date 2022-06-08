package com.example.zoostore.service;

import com.example.oauth2.model.User;
import com.example.oauth2.security.JWTTokenProvider;
import com.example.oauth2.service.UserService;
import com.example.oauth2.util.HttpUtils;
import com.example.zoostore.dto.request.ProfileDetailsDtoRequest;
import com.example.zoostore.utils.model.CustomerUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    public User edit(ProfileDetailsDtoRequest request) {
        verifyId(request.getId());
        User user = userService.getUserById(request.getId());
        CustomerUtils.CustomerDtoToUser(request,user);
        return userService.save(user);
    }

    public User getById(Long id) {
        verifyId(id);
        return userService.getUserById(id);
    }

    private void verifyId(Long id) {
        String jwt = HttpUtils.getJWT();
        User userByJwt = jwtTokenProvider.getUserByJwt(jwt);
        if (!Objects.equals(userByJwt.getId(), id)) {
            throw new IllegalArgumentException("You can only get your own profile");
        }
    }

}
