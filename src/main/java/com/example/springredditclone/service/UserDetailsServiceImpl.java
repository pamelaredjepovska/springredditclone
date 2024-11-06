package com.example.springredditclone.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;


// Class for fetching user data
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    // Dependency for accessing the DB  
    private final UserRepository userRepository;
    
    // Load the user's data during authentication based on the username
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        // If user not found, throw a custom exception
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user with " + username +" username found."));

        // Else, convert it into a Spring Security object
        // and pass the user's informations
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActivated(), true, true, true, getAuthorities("USER"));
    }

    // Return a list of user roles or permissions
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    } 
}
