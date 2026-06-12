package com.example.pkcn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/*", "/").permitAll()
                        .requestMatchers("/api/v1/categories/**", "/").permitAll()
                        .requestMatchers("/api/v1/product/feature").permitAll()
                        .requestMatchers("/api/v1/shop/*", "/").permitAll()
                        .requestMatchers("/api/v1/user-manage-address/*", "/").permitAll()
                        .anyRequest().authenticated()

                );

        return http.build();
    }
}