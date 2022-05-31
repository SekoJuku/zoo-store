package com.example.oauth2.service;

import com.example.oauth2.dto.request.AuthorityDtoRequest;
import com.example.exception.domain.BadRequestException;
import com.example.exception.domain.NotFoundException;
import com.example.oauth2.model.Authority;
import com.example.oauth2.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public Authority getById(Long id) {
        return authorityRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Authority", "id " + id));
    }

    public Authority getByName(String name) {
        return authorityRepository.findByName(name)
            .orElseThrow(() -> new NotFoundException("Authority", "name " + name));
    }

    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    public Authority createAuthority(AuthorityDtoRequest dto) {
        if (authorityRepository.existsByName(dto.getName().toLowerCase())) {
            throw new BadRequestException(String.format("Authority %s already exists", dto.getName()));
        }
        Authority authority = new Authority();
        authority.setName(dto.getName());
        return authorityRepository.save(authority);
    }
}
