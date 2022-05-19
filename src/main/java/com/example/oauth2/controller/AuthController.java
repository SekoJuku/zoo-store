package com.example.oauth2.controller;


import com.example.oauth2.dto.request.UserRegistrationDtoRequest;
import com.example.oauth2.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<HttpStatus> confirmRegistration(@RequestParam("token") String token,
                                                          @RequestParam("email") String email) {
        authService.confirmRegistration(token, email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody UserRegistrationDtoRequest dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }
}
