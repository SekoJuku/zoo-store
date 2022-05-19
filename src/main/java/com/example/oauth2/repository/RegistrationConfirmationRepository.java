package com.example.oauth2.repository;

import com.example.oauth2.model.RegistrationConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationConfirmationRepository extends JpaRepository<RegistrationConfirmation, Long> {
    Optional<RegistrationConfirmation> findByToken(String token);

    Optional<RegistrationConfirmation> findByTokenAndUser_Email(String token, String email);
}