package com.example.springredditclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springredditclone.model.RefreshToken;
import java.util.Optional;

// Repository interface for managing RefreshToken entities
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // Find a refresh token by its token value
    Optional<RefreshToken> findByToken(String token);
    
    // Delete a refresh token by its token value
    void deleteByToken(String token);
}
