package com.mpolitakis.bookstore;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.mpolitakis.bookstore.controllers.AuthenticationController;
import com.mpolitakis.bookstore.controllers.AuthenticationRequest;
import com.mpolitakis.bookstore.controllers.AuthenticationResponse;
import com.mpolitakis.bookstore.controllers.RegisterRequest;
import com.mpolitakis.bookstore.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthenticationControllerTest.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private AuthenticationController controller;

    @Test
    void testRegister() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        when(service.register(request)).thenReturn(request);

        ResponseEntity<RegisterRequest> responseEntity = controller.register(request);

        assertEquals(request, responseEntity.getBody());
    }

    @Test
    void testAuthenticate() throws JWTCreationException, IOException {

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        AuthenticationResponse expectedResponse = new AuthenticationResponse("testJwtToken");

        when(service.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = controller.authenticate(request);

        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
