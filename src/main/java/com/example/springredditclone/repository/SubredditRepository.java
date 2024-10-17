package com.example.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.Subreddit;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    // Find a specific Subreddit by its name
    Optional<Subreddit> findByName(String subredditName); 
}
