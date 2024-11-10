package com.example.springredditclone.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Value;


// Configuration class for managing security settings
@Configuration
public class SecurityConfig {
    
    // Load the RSA keys used for verification of JWT tokens
    @Value("${jwt.public.key}")
    RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    RSAPrivateKey privateKey;

    // Authenticate users
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing or stateless APIs
                .authorizeHttpRequests(
                    (authorize) -> authorize
                    .requestMatchers("/api/auth/**") // Include leading slash
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/subreddit")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
            .build();
    }

    // Provide PasswordEncoder bean, how passwords should be hashed (BCrypt)
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Set up the decoding (verification) process of tokens using the public RSA key
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    // Configure JWT encoding (creating tokens), using the private and public RSA keys for signing tokens
    @Bean
    JwtEncoder jwtEncoder() {
        // Create a JWK 
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();

        // Set up JWK Source (storage for JWK)
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        // Create and return JWT Encoder
        return new NimbusJwtEncoder(jwks);
    }
}
