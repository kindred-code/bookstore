package com.mpolitakis.bookstore;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.mpolitakis.bookstore.configuration.ApplicationConfiguration;
import com.mpolitakis.bookstore.configuration.JwtConfiguration;
import com.mpolitakis.bookstore.controllers.AuthenticationRequest;
import com.mpolitakis.bookstore.controllers.AuthenticationResponse;
import com.mpolitakis.bookstore.controllers.RegisterRequest;
import com.mpolitakis.bookstore.models.User;
import com.mpolitakis.bookstore.repositories.UserRepository;
import com.mpolitakis.bookstore.services.AuthenticationService;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

@SpringBootTest(classes = AuthenticationServiceTest.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ApplicationConfiguration applicationConfiguration;

    @Mock
    private JwtConfiguration jwtConfig;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegister() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        RegisterRequest result = authenticationService.register(request);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());

    }

    @Test
    void testAuthenticate_SuccessfulAuthentication() throws JWTCreationException, IOException {
        // Given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        User user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .build();

        when(repository.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("testPassword", "encodedPassword")).thenReturn(true);
        when(jwtConfig.createJwt("testUser")).thenReturn("testJwtToken");

        AuthenticationResponse result = authenticationService.authenticate(request);

        assertNotNull(result);
        assertEquals("testJwtToken", result.getAccessToken());

    }

    @Test
    void testAuthenticate_UserNotFound() {

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("nonexistentUser");
        request.setPassword("testPassword");

        when(repository.findByUsername("nonexistentUser")).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(ResponseStatusException.class, () -> authenticationService.authenticate(request));
    }

    @Test
    void testAuthenticate_IncorrectPassword() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testUser");
        request.setPassword("incorrectPassword");

        User user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .build();

        when(repository.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("incorrectPassword", "encodedPassword")).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> authenticationService.authenticate(request));
    }

}
