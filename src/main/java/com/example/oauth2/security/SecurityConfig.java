package com.example.oauth2.security;

import com.example.oauth2.environment.JWTEnvironmentBuilder;
import com.example.oauth2.exception.domain.BadRequestException;
import com.example.oauth2.exception.domain.NotFoundException;
import com.example.oauth2.exception.domain.UnauthorizedException;
import com.example.oauth2.service.AuthService;
import com.example.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final JWTEnvironmentBuilder jwtEnvironmentBuilder;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()
            .antMatchers(SecurityConstants.PUBLIC_URLS.PUBLIC).permitAll()
            .antMatchers(SecurityConstants.PUBLIC_URLS.NON_AUTHENTICATED).not().authenticated()
            .anyRequest().authenticated()

            .and()

            .formLogin()
            .usernameParameter("email")
            .passwordParameter("password")
            .failureHandler(((request, response, exception) -> {
                throw new UnauthorizedException();
            }))
            .successHandler(localAuthenticationSuccessHandler())

            .and()

            .oauth2Login()
            .successHandler(oAuth2AuthenticationSuccessHandler())

            .and()
            .addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), jwtEnvironmentBuilder, jwtTokenProvider))
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new UnauthorizedException(e);
        }
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring();
    }

    private AuthenticationSuccessHandler localAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user.getUsername(), request);
            response.setHeader(jwtEnvironmentBuilder.getJWT_TOKEN_HEADER(), token);
        };
    }



    private AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
            String authProviderName = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
            try {
                authService.registrationUserByOAuth(user.getName(), authProviderName);
                String token = jwtTokenProvider.generateToken(user.getName(), request);
                response.setHeader(jwtEnvironmentBuilder.getJWT_TOKEN_HEADER(), token);
            } catch (BadRequestException e) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } catch (NotFoundException e) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } catch (Exception e) {
                request.logout();
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        };
    }
}
