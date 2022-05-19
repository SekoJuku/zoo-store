package com.example.oauth2.service;

import com.example.oauth2.dto.request.UpdateUserRoleDtoRequest;
import com.example.oauth2.dto.request.UserRegistrationDtoRequest;
import com.example.oauth2.exception.domain.BadRequestException;
import com.example.oauth2.exception.domain.NotFoundException;
import com.example.oauth2.exception.domain.UnauthorizedException;
import com.example.oauth2.model.Role;
import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import com.example.oauth2.security.JWTTokenProvider;
import com.example.oauth2.security.SecurityConstants;
import com.example.oauth2.security.UserPrincipal;
import com.example.oauth2.util.HttpUtils;
import com.example.oauth2.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = this.getUserByEmail(email);
            return new UserPrincipal(user);
        }catch (NotFoundException e){
            throw new UsernameNotFoundException(e.getLocalizedMessage(), e);
        }
    }





    private String getIp() {
        return HttpUtils.getIp(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest());
    }


    public UserDetails findUserPrincipleByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(UnauthorizedException::new);
        return new UserPrincipal(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User", "email"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", "id"));
    }

    public void updateRole(UpdateUserRoleDtoRequest dto) {
        Role role = roleService.getRoleById(dto.getRoleId());
        User user = getUserById(dto.getUserId());
        user.setRole(role);
        userRepository.save(user);
    }

    public void updatePassword(Principal principal, UserRegistrationDtoRequest dto) {
        User user = getUserByEmail(principal.getName());
        if (user.getAuthProvider().getName().equals(SecurityConstants.LOCAL_AUTH_PROVIDER)) {
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
}
