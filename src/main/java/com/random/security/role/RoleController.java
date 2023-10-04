package com.random.security.role;

import com.random.security.role.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoleWithoutAdmin(){
        return ResponseEntity.ok().body(roleService.getAllRoleWithoutAdmin());
    }

    @GetMapping("/get-all-roles")
    public ResponseEntity<List<RoleDto>> getAllRole(){
        return ResponseEntity.ok().body(roleService.getAllRole());
    }
}
