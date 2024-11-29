package com.example.springredditclone.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import lombok.RequiredArgsConstructor;


// Service class for generating JWT
@Service
@RequiredArgsConstructor
public class JwtProvider {

    // Bean to encode the JWT
    private final JwtEncoder jwtEncoder;

    // Read the expiration time from the app configuration
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    // Create a token with the user's details
    public String generateToken(Authentication authentication) {
        // Get the user's username from the auth object
        User principal = (User) authentication.getPrincipal();

        return generateTokenWithUsername(principal.getUsername());
    }

    // Build the JWT using claims
    public String generateTokenWithUsername(String username) {
        // Build the token's payload containing username, issue time, expiration time and role
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope", "ROLE_USER")
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // Fetch the token expiration time
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
