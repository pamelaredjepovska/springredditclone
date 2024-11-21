package com.example.springredditclone.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springredditclone.dto.CommentDto;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.mapper.CommentMapper;
import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.NotificationEmail;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.CommentRepository;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

// Service class for managing user comments tasks
@Service
@AllArgsConstructor 
public class CommentService {

    // URL to be used in email notifications (can be customized later)
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    // Saves a new comment and notifies the post owner via email
    public void save(CommentDto CommentDto) {
        // Find the associated post or throw an exception if not found
        Post post = postRepository.findById(CommentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(CommentDto.getPostId().toString()));

        // Map the CommentDto to a Comment object, including the post and current user
        Comment comment = commentMapper.map(CommentDto, post, authService.getCurrentUser());

        // Save the comment to the database
        commentRepository.save(comment);

        // Build the notification email content
        String message = mailContentBuilder.build(post.getUser().getUsername() + 
                        " posted a comment on your post." + POST_URL);

        // Send the notification to the post owner
        sendCommentNotification(message, post.getUser());
    }

    // Sends a notification email to the specified user
    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(
                user.getUsername() + " commented on your post", 
                user.getEmail(), 
                message));
    }

    // Fetches all comments for a specific post, maps them to CommentDto objects, and returns the list
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        // Find the post or throw an exception if not found
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        // Retrieve and map the comments to DTOs
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

    // Fetches all comments made by a specific user, maps them to CommentDto objects, and returns the list
    public List<CommentDto> getAllCommentsForUser(String userName) {
        // Find the user by username or throw an exception if not found
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        // Retrieve and map the user's comments to DTOs
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }
}
