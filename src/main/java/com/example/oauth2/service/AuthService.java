package com.example.oauth2.service;


import com.example.oauth2.dto.request.AddUserDtoRequest;
import com.example.oauth2.dto.request.ResetPasswordDtoRequest;
import com.example.oauth2.dto.request.UserRegistrationDtoRequest;
import com.example.exception.domain.BadRequestException;
import com.example.exception.domain.NotFoundException;
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
import com.example.oauth2.security.JWTTokenProvider;
import com.example.oauth2.security.SecurityConstants;
import com.example.oauth2.security.UserPrincipal;
import com.example.oauth2.util.HttpUtils;
import com.example.oauth2.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;
import java.util.Random;

import static com.example.oauth2.security.SecurityConstants.LOCAL_AUTH_PROVIDER;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {
    public static final String AUTHORIZATION = "Authorization";
    private final UserMapper userMapper;
    private final MailSenderService mailSenderService;
    private final UserService userService;
    private final JWTTokenProvider jwtProvider;
    private final Random rd = new Random();


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
        AddUserDtoRequest addUserDtoRequest = new AddUserDtoRequest(dto.getEmail(), dto.getPassword(), dto.getRePassword(), null, null);
        registration(addUserDtoRequest);
    }

    public void registration(AddUserDtoRequest dto) {
        if (dto == null) {
            throw new BadRequestException("RequestDto is null");
        }
        register(dto, LOCAL_AUTH_PROVIDER);
    }

    public void registrationUserByOAuth(String email, String authProviderName) {
        if (!userRepository.existsByEmailAndAuthProvider_Name(email, authProviderName)) {
            register(new AddUserDtoRequest(email), authProviderName);
        }
    }

    public void register(AddUserDtoRequest dto, String authProviderName) {
        if (Strings.isBlank(dto.getEmail())) {
            throw new BadRequestException("Email is blank");
        }
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isPresent()) {
            throw new BadRequestException("User with this email is exists");
        }
        if (authProviderName.equals("local")) {
            if (!dto.getPassword().equals(dto.getRePassword())) {
                throw new BadRequestException("Passwords are not equal");
            }
            if (!PasswordUtil.isValidPassword(dto.getPassword())) {
                throw new BadRequestException("Invalid password");
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

    public void updatePassword(ResetPasswordDtoRequest dto) {
        User user = userService.getUserByEmail(dto.getEmail());
        if (user.getAuthProvider().getName().equals(SecurityConstants.LOCAL_AUTH_PROVIDER)) {
            if (user.getResetPasswordCode() == null || !user.getResetPasswordCode().equals(dto.getCode())) {
                throw new BadRequestException("Code is not valid!");
            }
            if (!dto.getPassword().equals(dto.getRePassword())) {
                throw new BadRequestException("Passwords are not equal");
            }
            if (!PasswordUtil.isValidPassword(dto.getPassword())) {
                throw new BadRequestException("Invalid password");
            }
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);
        }
    }

    private AuthProvider getAuthProviderByName(String authProviderName) {
        return authProviderRepository.findByName(authProviderName.toLowerCase())
                .orElseThrow(() -> new NotFoundException("AuthProvider", "name"));
    }

    public ResponseEntity<?> auth(String username, String password) {
        User user = userService.getUserByEmail(username);
        if (!user.getRegistrationConfirmed()) {
            throw new BadCredentialsException("User is locked");
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        if (passwordEncoder.matches(password, user.getPassword())) {
            HttpHeaders authorizationHeader = getJwtHeader(userPrincipal);
            return new ResponseEntity<>(user, authorizationHeader, HttpStatus.OK);
        }
        throw new BadCredentialsException("Username and password incorrect");
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, jwtProvider.generateToken(userPrincipal, HttpUtils.getIp()));
        return httpHeaders;
    }

    public void sendResetCode(String email) {
        User user = userService.getUserByEmail(email);
        long i = 100000L + rd.nextInt(900000);
        user.setResetPasswordCode(i);
        mailSenderService.sendEmail("Reset your password", String.format("Code: %s", i), email);
        userService.save(user);
    }

    public String getTokenFromOauth2(String email) {
        return userService.userByEmailAndProvider(email, "google").getToken();
    }
}
