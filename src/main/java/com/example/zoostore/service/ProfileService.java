package com.example.zoostore.service;

import com.example.oauth2.model.User;
import com.example.oauth2.service.UserService;
import com.example.zoostore.dto.request.ProfileDetailsDtoRequest;
import com.example.zoostore.utils.model.CustomerUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserService userService;


    public User edit(ProfileDetailsDtoRequest request) {
        User user = userService.getUserById(request.getId());
        CustomerUtils.CustomerDtoToUser(request,user);
        return userService.save(user);
    }

    public User getById(Long id) {
        return userService.getUserById(id);
    }
}
