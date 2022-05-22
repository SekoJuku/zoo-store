package com.example.oauth2.security;


import java.util.Arrays;

public class SecurityConstants {
    public static final String LOCAL_AUTH_PROVIDER = "local";

    public static final class ROLES {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
        public static final String CONTENT_MANAGER = "CONTENT_MANAGER";
    }

    public static class JWT {
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String SECRET = "89juw2HGSAbw72scD2SSw2!nAdnTwjOI64Se32Cr3t";
        public static final Long EXPIRATION_TIME = (long) (15 * 60 * 1000);//15 minutes
        public static final String CLIENT_IP = "Client ip";
    }

    public static final class PUBLIC_URLS {
        public static final String[] PUBLIC = {
            "/webjars/**",
            "/api/v1/pet",
            "/api/v1/pet/**"
        };

        public static final String[] NON_AUTHENTICATED = {
            "/login",
            "/api/v*/auth/**"
        };

        public static final String[] SWAGGER = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
        };

        public static boolean contains(String url) {
            return Arrays.asList(PUBLIC).contains(url)
                || Arrays.asList(NON_AUTHENTICATED).contains(url)
                || Arrays.asList(SWAGGER).contains(url);
        }
    }
}
