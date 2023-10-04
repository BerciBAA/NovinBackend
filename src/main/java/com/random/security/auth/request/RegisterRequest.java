package com.random.security.auth.request;

import com.random.security.role.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "The USERNAME should be not null!")
    @NotEmpty(message = "The USERNAME should be not empty!")
    @NotBlank(message = "The USERNAME should be not blank!")
    private String username;

    @NotNull(message = "The NAME should be not null!")
    @NotEmpty(message = "The NAME should be not empty!")
    @NotBlank(message = "The NAME should be not blank!")
    private String name;

    @NotNull(message = "The PASSWORD should be not null!")
    @NotEmpty(message = "The PASSWORD should be not empty!")
    @NotBlank(message = "The PASSWORD should be not blank!")
    private String password;

    @NotNull(message = "The ROLEID should be not null!")
    private int roleId;
}
