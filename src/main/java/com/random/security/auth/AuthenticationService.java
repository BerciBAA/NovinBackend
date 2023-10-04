package com.random.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.random.security.auth.request.AuthenticationRequest;
import com.random.security.auth.request.IsTokenValidRequest;
import com.random.security.auth.request.RegisterRequest;
import com.random.security.auth.response.AuthenticationResponse;
import com.random.security.auth.response.IsTokenValidResponse;
import com.random.security.config.JwtService;
import com.random.security.exceptions.ObjectNotValidException;
import com.random.security.exceptions.UsernameIsAlreadyExistsException;
import com.random.security.role.Role;
import com.random.security.role.RoleRepository;
import com.random.security.token.Token;
import com.random.security.token.TokenRepository;
import com.random.security.token.TokenType;
import com.random.security.user.User;
import com.random.security.user.UserService;
import com.random.security.validator.ObjectValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectValidator<RegisterRequest> registerValidator;
    private final ObjectValidator<AuthenticationRequest> AuthenticationValidator;
    public void register(RegisterRequest request) throws UsernameIsAlreadyExistsException {
        registerValidator.validate(request);
        if(isUsernameFree(request.getUsername()))
            throw new UsernameIsAlreadyExistsException("A felhasználói név foglalt!");
        System.out.println(request.getRoleId());
        var role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new ObjectNotValidException(Set.of("Nincs ilyen jogosultság!")));
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList(role))
                .build();
        User savedUser = userService.saveUser(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        AuthenticationValidator.validate(request);
        User user = userService.findByUsername(request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );
        Map<String,Object> claims = new HashMap<>();
        for (Role role : user.getRoles()){
            claims.put(role.getName(), role.getName());
        }
        String jwtToken = jwtService.generateToken(claims, user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, jwtToken);
        user.setLoginAt(LocalDateTime.now());
        userService.saveUser(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .roles(user.getRoles())
                .build();
    }

    public IsTokenValidResponse isTokenValid(IsTokenValidRequest request){

        boolean isTokenValid = jwtService.isTokenValid(request.getToken());
        String username = jwtService.extractUsername(request.getToken());
        User user = userService.findByUsername(username);
        Optional<Token> token = tokenRepository.findByToken(request.getToken());
        if(token.isPresent()){
            if(!token.get().revoked && !token.get().expired)
                return IsTokenValidResponse.builder().isValid(isTokenValid).build();
        }
        return IsTokenValidResponse.builder().isValid(false).build();
    }

    private void revokeAllUserToken(User user){
        List<Token> validTokenUsers = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokenUsers.isEmpty())
            return;
        validTokenUsers.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokenUsers);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if(username != null){
            User user = this.userService.findByUsername(username);

            if(jwtService.isTokenValid(refreshToken,user)){
                String accessToken = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authenticationResponse);
            }
        }
    }
    private boolean isUsernameFree(String username){
        return userService.existsUserByUsername(username);
    }



}
