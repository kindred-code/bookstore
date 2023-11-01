package com.mpolitakis.bookstore.configuration;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http

                                .csrf(csrf -> csrf
                                                .disable())
                                .authorizeHttpRequests()
                                .requestMatchers(
                                                "/api/login", "/api/register")
                                .permitAll()
                                .requestMatchers("**").authenticated()
                                .and()
                                .oauth2ResourceServer()
                                .jwt()

                ;

                return http.build();
        }
}
