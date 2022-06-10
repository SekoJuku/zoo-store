package com.example.oauth2.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(columnDefinition = "boolean default false")
    private Boolean registrationConfirmed;

    @Column(columnDefinition = "text")
    private String password;

    @OneToOne
    @JoinColumn(name = "auth_provider_id")
    private AuthProvider authProvider;

    @Setter(value = AccessLevel.NONE)
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private Boolean locked;

    @Column(columnDefinition = "TEXT")
    private String token;

    private String gender;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String country;

    private String city;

    private Long resetPasswordCode;

    @PrePersist
    private void prePersist() {
        this.createdTime = LocalDateTime.now();
        this.locked = false;
    }
}