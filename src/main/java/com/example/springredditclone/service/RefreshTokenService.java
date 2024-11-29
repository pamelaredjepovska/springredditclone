package com.example.springredditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.RefreshToken;
import com.example.springredditclone.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

// Service class for managing Refresh Token operations
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    // Generate a new refresh token and save it to the database
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    // Validate the provided refresh token, throwing an exception if invalid
    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new SpringRedditException("Invalid refresh token."));
    }

    // Delete a refresh token by its token value
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
