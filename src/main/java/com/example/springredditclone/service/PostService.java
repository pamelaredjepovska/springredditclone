package com.example.springredditclone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SubredditNotFoundException;
import com.example.springredditclone.mapper.PostMapper;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.SubredditRepository;
// import com.example.springredditclone.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

// Service class for managing post-related operations
@Service
@AllArgsConstructor 
@Slf4j 
@Transactional 
public class PostService {

    // Dependencies required for post operations
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    // private final UserRepository userRepository; // Not currently used
    private final AuthService authService;
    private final PostMapper postMapper;

    // Saves a new post
    public void save(PostRequest postRequest) {
        // Retrieve the subreddit by name or throw an exception if not found
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

        // Map the PostRequest to a Post entity and save it to the repository
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    // Retrieves a post by its ID
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        // Fetch the post from the repository or throw an exception if not found
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));

        // Map the Post entity to a PostResponse DTO and return it
        return postMapper.mapToDto(post);
    }

    // Retrieves all posts
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        // Fetch all posts, map them to PostResponse DTOs, and collect them into a list
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
