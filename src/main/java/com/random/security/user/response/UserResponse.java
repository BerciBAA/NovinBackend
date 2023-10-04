package com.random.security.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.random.security.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String name;
    private String username;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginAt;
    private List<Role> roles;
}
