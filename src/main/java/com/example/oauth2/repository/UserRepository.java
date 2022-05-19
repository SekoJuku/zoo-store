package com.example.oauth2.repository;

import com.example.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndAuthProvider_Name(String email, String authProviderName);

    Optional<User> findByEmail(String email);
}
