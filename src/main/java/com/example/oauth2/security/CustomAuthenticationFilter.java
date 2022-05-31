package com.example.oauth2.security;

import com.example.oauth2.dto.request.UserLoginDtoRequest;
import com.example.oauth2.environment.JWTEnvironmentBuilder;
import com.example.exception.domain.UnauthorizedException;
import com.example.oauth2.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTEnvironmentBuilder jwtEnvironmentBuilder;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginDtoRequest userLogin;
        try {
            userLogin = new ObjectMapper().readValue(request.getInputStream(), UserLoginDtoRequest.class);
        } catch (IOException e) {
            throw new UnauthorizedException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(user, HttpUtils.getIp(request));
        response.setHeader(jwtEnvironmentBuilder.getJWT_TOKEN_HEADER(), token);
    }
}
