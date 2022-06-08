package com.example.oauth2.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.oauth2.environment.JWTEnvironmentBuilder;
import com.example.oauth2.mappers.UserMapper;
import com.example.oauth2.model.User;
import com.example.oauth2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTTokenProvider {

    private final JWTEnvironmentBuilder jwtEnvironmentBuilder;
    private final UserService userService;

    public String generateToken(String email, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        User user = userService.getUserByEmail(email);
        return generateToken(new UserPrincipal(user), clientIp);
    }

    public String generateToken(UserPrincipal userPrincipal, String ipFromClient) {
        return JWT.create()
            .withIssuer(jwtEnvironmentBuilder.getISSUER())
            .withAudience(jwtEnvironmentBuilder.getAUDIENCE())
            .withIssuedAt(new Date())
            .withSubject(userPrincipal.getId().toString())
            .withClaim(jwtEnvironmentBuilder.getCLIENT_IP(), ipFromClient)
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtEnvironmentBuilder.getEXPIRATION_TIME()))
            .sign(Algorithm.HMAC512(jwtEnvironmentBuilder.getSECRET().getBytes()));
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    @SneakyThrows
    public User getUserByJwt(String token) {
        JWTVerifier verifier = getJWTVerifier();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(verifier.verify(token).getPayload(), User.class);
        return user;
    }

    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(email, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier jwtVerifier;

        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtEnvironmentBuilder.getSECRET());
            jwtVerifier = JWT.require(algorithm).withIssuer(jwtEnvironmentBuilder.getISSUER()).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(jwtEnvironmentBuilder.getTOKEN_CANNOT_BE_VERIFIED());
        }
        return jwtVerifier;
    }
}
