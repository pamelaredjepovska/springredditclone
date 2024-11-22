package com.example.springredditclone.dto;

import com.example.springredditclone.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for transferring vote data from the client to the server
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType; // Type of vote (upvote or downvote)
    private Long postId; // ID of the post the vote belongs to
}
