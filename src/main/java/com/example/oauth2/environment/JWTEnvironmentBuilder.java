package com.example.oauth2.environment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JWTEnvironmentBuilder {
    @Value("${security.token.prefix}")
    private String TOKEN_PREFIX;

    @Value("${security.token.client-ip}")
    private String CLIENT_IP;

    @Value("${security.token.secret}")
    private String SECRET;

    @Value("${security.token.token-cannot-be-verified}")
    private String TOKEN_CANNOT_BE_VERIFIED;

    @Value("${security.token.issuer}")
    private String ISSUER;

    @Value("${security.token.audience}")
    private String AUDIENCE;

    @Value("${security.token.expiration-time}")
    private Long EXPIRATION_TIME;

    @Value("${security.token.jwt-token-header}")
    private String JWT_TOKEN_HEADER;
}
