package com.example.zoostore.controller;

import com.example.oauth2.model.User;
import com.example.zoostore.dto.request.ProfileDetailsDtoRequest;
import com.example.zoostore.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("")
    public User getById() {
        return profileService.getById();
    }

    @PutMapping("")
    public User edit(@RequestBody ProfileDetailsDtoRequest request) {
        return profileService.edit(request);
    }
}
