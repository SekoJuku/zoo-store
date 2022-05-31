package com.example.oauth2.service;

import com.example.oauth2.dto.response.RoleDtoResponse;
import com.example.exception.domain.NotFoundException;
import com.example.oauth2.mappers.RoleMapper;
import com.example.oauth2.model.Role;
import com.example.oauth2.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role getRoleById(Long id){
        return roleRepository.findById(id).orElseThrow(()->new NotFoundException("Role", "id"));
    }

    public List<RoleDtoResponse> getAllRoles(){
        return roleRepository.findAll().stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }

    public void addRole() {

    }
}
