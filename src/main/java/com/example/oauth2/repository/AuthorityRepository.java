package com.example.oauth2.repository;

import com.example.oauth2.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    boolean existsByName(String name);

    Optional<Authority> findByName(String name);
}