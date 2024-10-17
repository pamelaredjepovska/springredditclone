package com.example.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Find all posts assosicated with a specific subreddit
    List<Post> findAllBySubreddit(Subreddit subreddit);

    // Find all posts made by a specific user
    List<Post> findByUser(User user);
}
