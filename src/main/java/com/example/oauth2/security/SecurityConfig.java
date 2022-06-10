package com.example.oauth2.security;

import com.example.oauth2.environment.JWTEnvironmentBuilder;
import com.example.exception.domain.BadRequestException;
import com.example.exception.domain.NotFoundException;
import com.example.exception.domain.UnauthorizedException;
import com.example.oauth2.model.User;
import com.example.oauth2.service.AuthService;
import com.example.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
                .antMatchers(SecurityConstants.PUBLIC_URLS.USER).hasRole("USER_ROLE")
                .anyRequest().authenticated()
                .and()

//            .formLogin()
//            .usernameParameter("email")
//            .passwordParameter("password")
//            .failureHandler(((request, response, exception) -> {
//                throw new UnauthorizedException();
//            }))
//            .successHandler(localAuthenticationSuccessHandler())

//            .and()

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
                log.info(authProviderName + ": " + user.getName());
                authService.registrationUserByOAuth(user.getName(), authProviderName);
                String token = jwtTokenProvider.generateToken(user.getName(), request);
                User user1 = userService.userByEmailAndProvider(user.getName(), authProviderName);
                log.info("user: " + user1);
                user1.setToken(token);
                userService.save(user1);
                response.setHeader(jwtEnvironmentBuilder.getJWT_TOKEN_HEADER(), token);
            } catch (BadRequestException e) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } catch (NotFoundException e) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            } catch (Exception e) {
                request.logout();
                log.error(e.getMessage());
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        };
    }
}
