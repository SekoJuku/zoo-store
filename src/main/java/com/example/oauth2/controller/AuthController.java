package com.example.oauth2.controller;


import com.example.oauth2.dto.request.ResetPasswordDtoRequest;
import com.example.oauth2.dto.request.UserLoginDtoRequest;
import com.example.oauth2.dto.request.UserRegistrationDtoRequest;
import com.example.oauth2.service.AuthService;
import com.example.oauth2.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping(value = "/confirm-registration", produces = "text/html")
    public String confirmRegistration(@RequestParam("token") String token,
                                                          @RequestParam("email") String email) {
        authService.confirmRegistration(token, email);
        return "<div style=\"display:flex;margin:auto;align-items:center;text-align:center;justify-content: center;\"><svg width=\"50px\" height=\"50px\"><image xlink:href=\"https://www.svgrepo.com/show/218199/confirm.svg\" width=\"50\" height=\"50\"></svg><h1>Your account has been successfully activated</h1></div>";
    }

    @GetMapping("/code")
    public ResponseEntity<HttpStatus> getResetCode(@RequestParam("email") String email) {
        authService.sendResetCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ResetPasswordDtoRequest request) {
        authService.updatePassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<?> auth(@RequestBody UserLoginDtoRequest request) {
        return authService.auth(request.getEmail(), request.getPassword());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody UserRegistrationDtoRequest dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }
}
