package com.random.security.auth;

import com.random.security.auth.request.AuthenticationRequest;
import com.random.security.auth.request.IsTokenValidRequest;
import com.random.security.auth.request.RegisterRequest;
import com.random.security.auth.response.AuthenticationResponse;
import com.random.security.auth.response.IsTokenValidResponse;
import com.random.security.exceptions.UsernameIsAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PutMapping("/register")
    public ResponseEntity register(
            @RequestBody RegisterRequest request
    ) throws UsernameIsAlreadyExistsException {
        authenticationService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authentication(request));
    }

    @PostMapping("/is-valid-token")
    public ResponseEntity<IsTokenValidResponse> isTokenValid(
            @RequestBody IsTokenValidRequest request
    ){
        return ResponseEntity.ok(authenticationService.isTokenValid(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
        return ResponseEntity.ok().build();
    }


}
