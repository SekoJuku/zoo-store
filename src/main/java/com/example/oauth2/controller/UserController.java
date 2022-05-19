package com.example.oauth2.controller;

import com.example.oauth2.dto.request.AddUserDtoRequest;
import com.example.oauth2.dto.request.UpdateUserRoleDtoRequest;
import com.example.oauth2.dto.response.UserDtoResponse;
import com.example.oauth2.mappers.UserMapper;
import com.example.oauth2.model.User;
import com.example.oauth2.service.AuthService;
import com.example.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority('getAllUsers')")
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll().stream().map(userMapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/user/id")
    @PreAuthorize("hasAuthority('getUserById')")
    public ResponseEntity<UserDtoResponse> getUserById(@RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('addUser')")
    public ResponseEntity<HttpStatus> addUser(@RequestBody AddUserDtoRequest dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(("/update-role"))
    @PreAuthorize("hasAuthority('updateUserRole')")
    public ResponseEntity<HttpStatus> updateUserRole(@RequestBody UpdateUserRoleDtoRequest dto) {
        userService.updateRole(dto);
        return ResponseEntity.ok().build();
    }
}
