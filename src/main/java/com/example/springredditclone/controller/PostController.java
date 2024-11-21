package com.example.springredditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.service.PostService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.ResponseEntity.status;


// REST Controller for managing post-related endpoints
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    
    // Endpoint for creating a new post
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        // Delegates the save operation to the service layer
        postService.save(postRequest);

        // Returns HTTP 201 (Created) response with no body
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Endpoint for fetching all posts
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        // Calls the service layer to fetch the posts
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    // Endpoint for fetchin a post associated with a specific ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        // Calls the service layer to fetch the post
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    // @GetMapping(params = "subredditId")
    // public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@RequestParam Long subredditId) {
    //     return status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
    // }

    // @GetMapping(params = "username")
    // public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam String username) {
    //     return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    // }
}
