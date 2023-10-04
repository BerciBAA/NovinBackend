package com.random.security.role;

import com.random.security.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleDto> getAllRoleWithoutAdmin(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> RoleDto.builder()
                        .name(role.getName())
                        .id(role.getId())
                        .build())
                .filter(role -> !role.getName().equals("ADMIN"))
                .collect(Collectors.toList());
    }

    public List<RoleDto> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> RoleDto.builder()
                        .name(role.getName())
                        .id(role.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
