package com.example.user.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Use permitAll() to avoid 403 errors if a token is accidentally sent
                        .requestMatchers(HttpMethod.POST, "/user/signup", "/user/login").permitAll()

                        // Removed the extra escaped quotes around the actuator path
                        .requestMatchers(HttpMethod.GET, "/actuator/beans").permitAll()

                        // Require a valid JWT for everything else
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Creates a BCrypt hasher with a default strength of 10
        return new BCryptPasswordEncoder();
    }
}