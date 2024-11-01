package com.example.springredditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Allow all requests that match "api/auth/*" with no authentication
        // All other requests require authentication
        return httpSecurity
                .authorizeHttpRequests(
                    (authorize) -> authorize
                    .requestMatchers("api/auth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
            .build();
    }
}
