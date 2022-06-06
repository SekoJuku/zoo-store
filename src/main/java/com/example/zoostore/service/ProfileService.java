package com.example.zoostore.service;

import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import com.example.oauth2.service.UserService;
import com.example.zoostore.dto.request.CustomerDetailsDtoRequest;
import com.example.zoostore.utils.model.CustomerUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserService userService;


    public User edit(CustomerDetailsDtoRequest request) {
        User user = userService.getUserById(request.getId());
        CustomerUtils.CustomerDtoToUser(request,user);
        return userService.save(user);
    }

    public User getById(Long id) {
        return userService.getUserById(id);
    }
}
