package com.example.springredditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.VerificationToken;
import com.example.springredditclone.repository.UserRepository;
import com.example.springredditclone.repository.VerificationTokenRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder = null;
    private final UserRepository userRepository = null;
    private final VerificationTokenRepository verificationTokenRepository = null;

    public void signup(RegisterRequest registerRequest) {
        // Create new User object with the specified information
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setActivated(false);

        // Write it into the DB
        userRepository.save(user);

        // Generate verification token
        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        // Generate random UUID as a verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        // Write it into the DB for the specified user
        verificationTokenRepository.save(verificationToken);
        
        return token;
    }
}
