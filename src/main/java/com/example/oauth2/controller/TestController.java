package com.example.oauth2.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/oauth")
    public ResponseEntity<?> testOAuth2(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
