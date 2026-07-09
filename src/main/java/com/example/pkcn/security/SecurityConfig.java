package com.example.pkcn.security;

import com.example.pkcn.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    //Nếu không set UserDetails vô context security thì gặp authenticated sẽ 403
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/").permitAll()
                        .requestMatchers("/test-voucher-json", "/").permitAll() //test
                        .requestMatchers("/test-order-voucher", "/").permitAll() //test
                        .requestMatchers("/api/v1/admin-order/**", "/").permitAll()
                        .requestMatchers("/api/v1/categories/**", "/").permitAll()
                        .requestMatchers("/api/v1/product/**").permitAll()
                        .requestMatchers("/api/v1/ai/**", "/").permitAll()
                        .requestMatchers("/api/v1/shop/**", "/").permitAll()
                        .requestMatchers("/api/v1/slider-show/**").permitAll()
                        .requestMatchers("/api/v1/payment-methods/**").permitAll()
                        .requestMatchers("/api/v1/user-manage-address/**", "/").authenticated()
                        .requestMatchers("/api/v1/cart/**").authenticated()
                        .requestMatchers("/api/v1/ship-fee-address/**", "/").permitAll()
                        .requestMatchers("/api/v1/admin-product/**", "/").hasRole("ADMIN")
                        .requestMatchers("/api/v1/admin-voucher/**", "/").hasRole("ADMIN")
                        .requestMatchers("/api/v1/orders/**").authenticated()
                        .anyRequest().authenticated()
                ).sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}