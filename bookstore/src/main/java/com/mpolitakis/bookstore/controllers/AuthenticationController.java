package com.mpolitakis.bookstore.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.mpolitakis.bookstore.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "${spring.application.cors.origin}" })
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<RegisterRequest> register(
      @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) throws IllegalArgumentException, JWTCreationException, IOException {

    return ResponseEntity.ok(service.authenticate(request));
  }

}
