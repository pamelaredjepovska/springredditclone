package com.example.springredditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.NotificationEmail;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.VerificationToken;
import com.example.springredditclone.repository.UserRepository;
import com.example.springredditclone.repository.VerificationTokenRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    
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

        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the link below to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
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

    public void verifyAccount(String token) {
        // Find the verification token and activate the user
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndActivate(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
            }
        
    private void fetchUserAndActivate(VerificationToken verificationToken) {
        // Get the username from the token
        String username = verificationToken.getUser().getUsername();

        // Find the user, if it does not exist, throw an exception
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User" + username + "not found."));

        // Activate the user account and save it to DB
        user.setActivated(true);
        userRepository.save(user);
    }
}
