package com.example.zoostore.controller;

import com.example.oauth2.model.User;
import com.example.zoostore.dto.request.CustomerDetailsDtoRequest;
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

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return profileService.getById(id);
    }

    @PutMapping("")
    public User edit(@RequestBody CustomerDetailsDtoRequest request) {
        return profileService.edit(request);
    }
}
