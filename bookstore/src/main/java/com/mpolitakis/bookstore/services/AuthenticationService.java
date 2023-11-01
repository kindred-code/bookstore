package com.mpolitakis.bookstore.services;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.mpolitakis.bookstore.configuration.JwtConfiguration;
import com.mpolitakis.bookstore.controllers.AuthenticationRequest;
import com.mpolitakis.bookstore.controllers.AuthenticationResponse;
import com.mpolitakis.bookstore.controllers.RegisterRequest;
import com.mpolitakis.bookstore.models.User;
import com.mpolitakis.bookstore.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfiguration jwtConfig;
    private final AuthenticationManager authenticationManager;

    public RegisterRequest register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);

        return request;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws IllegalArgumentException, JWTCreationException, IOException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        User user;
        try {
            user = repository.findByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            String jwt = jwtConfig.createJwt(request.getUsername());
            return new AuthenticationResponse(jwt);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

}
