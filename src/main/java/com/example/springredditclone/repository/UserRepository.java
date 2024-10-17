package com.example.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find the user by a specific username
    Optional<User> findByUsername(String username);
}
