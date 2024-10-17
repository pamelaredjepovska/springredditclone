package com.example.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    // Find all comments that are associated with a specific post
    List<Comment> findByPost(Post post);
    
    // Find all comments made by a specifid User
    List<Comment> findAllByUser(User user);
}
