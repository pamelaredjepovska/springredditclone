package com.example.springredditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.AuthenticationResponse;
import com.example.springredditclone.dto.LoginRequest;
import com.example.springredditclone.dto.RegisterRequest;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.NotificationEmail;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.VerificationToken;
import com.example.springredditclone.repository.UserRepository;
import com.example.springredditclone.repository.VerificationTokenRepository;
import com.example.springredditclone.security.JwtProvider;

import lombok.AllArgsConstructor;


// Service class for managing user authentication tasks
@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    
    // Register the user
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

        // Send an activation email to the user
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the link below to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    // Generate the user's verification token
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


    // Activate the user's account
    public void verifyAccount(String token) {
        // Find the verification token
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        // If it exists, find the user and activate the account.
        // Otherwise, throw an exception
        fetchUserAndActivate(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
    }
        

    // Activate the user's account associated with a given verification token
    private void fetchUserAndActivate(VerificationToken verificationToken) {
        // Get the username from the token
        String username = verificationToken.getUser().getUsername();

        // Find the user, if it does not exist, throw an exception
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User" + username + "not found."));

        // Activate the user account and save it to DB
        user.setActivated(true);
        userRepository.save(user);
    }

    // Login the user
    public AuthenticationResponse login(LoginRequest loginRequest) {
        // Authenticate the user's credentials
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Mark the user as authenticated within the session
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // Generate a JWT token using the user's details
        String token = jwtProvider.generateToken(authenticate);
        
        // Return an object with the token, expiration time and username
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                // .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    // Get current logged in user
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User not found - " + principal.getSubject()));
    }

    // Checks if the current user is logged in by evaluating the authentication context
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
