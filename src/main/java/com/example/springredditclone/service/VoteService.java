package com.example.springredditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.example.springredditclone.model.VoteType.UPVOTE;

// Service class for managing votes on posts
@Service
@AllArgsConstructor
public class VoteService {

    // Repositories and services required for vote handling
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    // Handles voting logic (upvote or downvote)
    @Transactional
    public void vote(VoteDto voteDto) {
        // Retrieve the post being voted on, or throw an exception if not found
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        
        // Check if the user has already voted on this post
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            // If the user tries to vote the same way again, throw an exception
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d this post");
        }

        // Update the post's vote count based on the vote type
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1); // Increment vote count for upvote
        } else {
            post.setVoteCount(post.getVoteCount() - 1); // Decrement vote count for downvote
        }

        // Save the vote and update the post
        voteRepository.save(mapToVote(voteDto, post)); // Map and save the vote entity
        postRepository.save(post); // Update the post entity
    }

    // Maps a VoteDto to a Vote entity
    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType()) // Set vote type (UPVOTE/DOWNVOTE)
                .post(post) // Associate the vote with the post
                .user(authService.getCurrentUser()) // Associate the vote with the current user
                .build(); // Build the Vote object
    }
}
