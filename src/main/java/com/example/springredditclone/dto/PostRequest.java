package com.example.springredditclone.dto;

import jakarta.validation.constraints.NotBlank; // Ensures the annotated field is not blank
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for transferring post creation data from the client to the server
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId; // Unique identifier for the post (used for updates)
    private String subredditName; // Name of the subreddit the post belongs to
    @NotBlank(message = "Post Name cannot be empty or Null") // Validates the title field
    private String title; // Title of the post
    private String url; // URL associated with the post (optional)
    private String description; // Description or content of the post
}
