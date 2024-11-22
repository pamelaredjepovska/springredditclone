package com.example.springredditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.model.VoteType;
import com.example.springredditclone.repository.CommentRepository;
import com.example.springredditclone.repository.VoteRepository;
import com.example.springredditclone.service.AuthService;

import static com.example.springredditclone.model.VoteType.DOWNVOTE;
import static com.example.springredditclone.model.VoteType.UPVOTE;

import java.util.Optional;

// MapStruct interface for mapping between Post and PostDto objects
@Mapper(componentModel = "spring")
public abstract class PostMapper {

    // Required dependencies
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    // Maps a PostDto object to a Post object
    @Mapping(target = "id", ignore = true) // Avoid conflicts with multiple 'id' sources
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    // Maps a Post object to a PostDto object
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    // Calculates the total number of comments associated with a given post
    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    // Retrieves the time elapsed since the post was created, in a human-readable format
    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    // Checks if the currently logged-in user has upvoted the specified post
    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    // Checks if the currently logged-in user has downvoted the specified post
    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    // Checks if the currently logged-in user has cast a vote of a specific type (upvote or downvote) on the specified post
    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByIdDesc(post,
                            authService.getCurrentUser());

            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }

        return false;
    }
}
