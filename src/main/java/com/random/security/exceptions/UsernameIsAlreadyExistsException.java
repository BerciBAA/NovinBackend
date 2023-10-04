package com.random.security.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UsernameIsAlreadyExistsException extends Exception{
    private String message;
}
