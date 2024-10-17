package com.example.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // Retrieve the most recent vote by a specific user on a given post
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
