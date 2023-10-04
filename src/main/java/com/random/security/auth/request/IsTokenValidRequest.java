package com.random.security.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsTokenValidRequest {

    @NotNull(message = "The TOKEN should be not null!")
    @NotEmpty(message = "The TOKEN should be not empty!")
    @NotBlank(message = "The TOKEN should be not blank!")
    private String token;
}
