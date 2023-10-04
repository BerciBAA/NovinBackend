package com.random.security.handler;

import com.random.security.exceptions.ObjectNotValidException;
import com.random.security.exceptions.UsernameIsAlreadyExistsException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException exp){
        return ResponseEntity
                .badRequest()
                .body(exp.getErrorMessages());
    }

    @ExceptionHandler(UsernameIsAlreadyExistsException.class)
    public ResponseEntity<?> handleException(UsernameIsAlreadyExistsException exp){
        return ResponseEntity
                .badRequest()
                .body(exp.getMessage());
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleException(UsernameNotFoundException exp){
        return ResponseEntity
                .badRequest()
                .body(exp.getMessage());
    }
}
