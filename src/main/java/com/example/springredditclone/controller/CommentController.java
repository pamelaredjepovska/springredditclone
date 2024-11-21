package com.example.springredditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springredditclone.dto.CommentDto;
import com.example.springredditclone.service.CommentService;

import lombok.AllArgsConstructor;

// REST Controller for managing comment-related endpoints
@RestController
@RequestMapping("api/comment") 
@AllArgsConstructor 
public class CommentController {

    // Dependency on CommentService for handling business logic
    private final CommentService commentService;

    // Endpoint for creating a new comment
    @PostMapping 
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentsDto) {
        // Delegates the save operation to the service layer
        commentService.save(commentsDto);

        // Returns HTTP 201 (Created) response with no body
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint for fetching all comments associated with a specific post
    @GetMapping(params = "postId")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@RequestParam Long postId) {
        // Calls the service layer to fetch the comments for the given post ID
        return ResponseEntity.status(HttpStatus.OK) // Returns HTTP 200 (OK)
                .body(commentService.getAllCommentsForPost(postId)); // Attaches the comments in the response body
    }

    // Endpoint for fetching all comments made by a specific user
    @GetMapping(params = "userName")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@RequestParam String userName) {
        // Calls the service layer to fetch the comments made by the given user
        return ResponseEntity.status(HttpStatus.OK) // Returns HTTP 200 (OK)
                .body(commentService.getAllCommentsForUser(userName)); // Attaches the comments in the response body
    }
}
