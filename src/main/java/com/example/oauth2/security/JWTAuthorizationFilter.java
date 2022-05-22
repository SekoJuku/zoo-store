package com.example.oauth2.security;

import com.example.oauth2.environment.JWTEnvironmentBuilder;
import com.example.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTEnvironmentBuilder jwtEnvironmentBuilder;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityConstants.PUBLIC_URLS.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith(jwtEnvironmentBuilder.getTOKEN_PREFIX())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(jwtEnvironmentBuilder.getTOKEN_PREFIX().length() + 1);

        try {
            Long userId = Long.parseLong(jwtTokenProvider.getSubject(token));
            UserPrincipal userPrincipal = (UserPrincipal) userService.findUserPrincipleByUserId(userId);
            if (!userPrincipal.isAccountNonLocked() || !userPrincipal.isEnabled()) {
                response.setStatus(UNAUTHORIZED.value());
                return;
            }
            List<GrantedAuthority> authorityList = new ArrayList<>();
            if (userPrincipal.getAuthorities() != null) {
                authorityList = new LinkedList<>(userPrincipal.getAuthorities());
            }
            Authentication authentication = jwtTokenProvider.getAuthentication(userPrincipal.getUsername(), authorityList, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(UNAUTHORIZED.value());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
