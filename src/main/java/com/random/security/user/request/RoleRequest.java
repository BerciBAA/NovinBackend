package com.random.security.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {

    @NotNull(message = "The USERID should be not null!")
    private int userId;
    @NotNull(message = "The ROLE should be not null!")
    @NotEmpty(message = "The ROLE should be not empty!")
    @NotBlank(message = "The ROLE should be not blank!")
    private String role;
}
