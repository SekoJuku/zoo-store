package com.example.oauth2.service;


import com.example.oauth2.dto.request.AddUserDtoRequest;
import com.example.oauth2.dto.request.UserRegistrationDtoRequest;
import com.example.oauth2.exception.domain.BadRequestException;
import com.example.oauth2.exception.domain.NotFoundException;
import com.example.oauth2.mailsender.MailSenderService;
import com.example.oauth2.mappers.UserMapper;
import com.example.oauth2.model.AuthProvider;
import com.example.oauth2.model.RegistrationConfirmation;
import com.example.oauth2.model.Role;
import com.example.oauth2.model.User;
import com.example.oauth2.repository.AuthProviderRepository;
import com.example.oauth2.repository.RegistrationConfirmationRepository;
import com.example.oauth2.repository.RoleRepository;
import com.example.oauth2.repository.UserRepository;
import com.example.oauth2.security.SecurityConstants;
import com.example.oauth2.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

import static com.example.oauth2.security.SecurityConstants.LOCAL_AUTH_PROVIDER;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final MailSenderService mailSenderService;
    private final UserService userService;


    private final UserRepository userRepository;
    private final AuthProviderRepository authProviderRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationConfirmationRepository registrationConfirmationRepository;

    public void confirmRegistration(String token, String email) {
        RegistrationConfirmation registrationConfirmation = registrationConfirmationRepository.findByTokenAndUser_Email(token, email)
            .orElseThrow(() -> new NotFoundException("Invalid token"));
        if (registrationConfirmation.getCreatedDate().plusHours(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() <= System.currentTimeMillis()) {
            throw new BadRequestException("Token is expired");
        }

        if (registrationConfirmation.getToken().equals(token) && registrationConfirmation.getUser().getEmail().equals(email)) {
            registrationConfirmation.getUser().setRegistrationConfirmed(true);
            userRepository.save(registrationConfirmation.getUser());
            return;
        }

        throw new BadRequestException("Invalid link");
    }

    public void registration(UserRegistrationDtoRequest dto) {
        AddUserDtoRequest addUserDtoRequest = new AddUserDtoRequest(dto.getEmail(), dto.getPassword(), dto.getRePassword(), null);
        registration(addUserDtoRequest);
    }

    public void registration(AddUserDtoRequest dto) {
        if (dto == null) {
            throw new BadRequestException("RequestDto is null");
        }
        if (!dto.getPassword().equals(dto.getRePassword())) {
            throw new BadRequestException("Passwords are not equal");
        }
        if (!PasswordUtil.isValidPassword(dto.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        register(dto, LOCAL_AUTH_PROVIDER);
    }

    public void registrationUserByOAuth(String email, String authProviderName) {
        if (!userRepository.existsByEmailAndAuthProvider_Name(email, authProviderName)) {
            register(new AddUserDtoRequest(email), authProviderName);
        }
    }

    private void register(AddUserDtoRequest dto, String authProviderName) {
        if (Strings.isBlank(dto.getEmail())) {
            throw new BadRequestException("Email is blank");
        }

        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getAuthProvider().getName().equals(LOCAL_AUTH_PROVIDER)) {
                if (user.getRegistrationConfirmed()) {
                    throw new BadRequestException("User with this email is exists");
                }
            } else {
                throw new BadRequestException("User with this email is exists");
            }
        }

        Role role;
        if (dto.getRoleId() == null) {
            role = roleRepository.findByName(SecurityConstants.ROLES.USER)
                .orElseThrow(() -> new NotFoundException("RoleId is null and role USER is not found"));
        } else {
            role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role", "id"));
        }

        AuthProvider authProvider = getAuthProviderByName(authProviderName);

        String password = Strings.isBlank(dto.getPassword()) ? null : passwordEncoder.encode(dto.getPassword());

        User user;

        if (optionalUser.isEmpty()) {
            user = User.builder()
                .email(dto.getEmail())
                .password(password)
                .role(role)
                .authProvider(authProvider)
                .registrationConfirmed(!authProviderName.equals(LOCAL_AUTH_PROVIDER))
                .build();
        } else {
            user = optionalUser.get();
            user.setPassword(password);
            user.setRole(role);
            user.setAuthProvider(authProvider);
        }

        userRepository.save(user);

        if (authProviderName.equals(LOCAL_AUTH_PROVIDER)) {
            mailSenderService.sendRegistrationConfirmation(user);
        }
    }

    private AuthProvider getAuthProviderByName(String authProviderName) {
        return authProviderRepository.findByName(authProviderName.toLowerCase())
            .orElseThrow(() -> new NotFoundException("AuthProvider", "name"));
    }
}
