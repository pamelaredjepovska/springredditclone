package com.example.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for transferring post data from the server to the client
@Data 
@AllArgsConstructor
@NoArgsConstructor 
public class PostResponse {
    private Long id; // Unique identifier for the post
    private String title; // Title of the post
    private String url; // URL associated with the post
    private String description; // Description or content of the post
    private String userName; // Username of the post creator
    private String subredditName; // Name of the subreddit the post belongs to
    private Integer voteCount; // Number of votes the post has received
    private Integer commentCount; // Number of comments on the post
    private String duration; // Time duration since the post was created (e.g., "2 hours ago")
    private boolean upVote; // Whether the current user has upvoted the post
    private boolean downVote; // Whether the current user has downvoted the post
}
