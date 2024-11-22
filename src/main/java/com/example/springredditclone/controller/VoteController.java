package com.example.springredditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.service.VoteService;

import lombok.AllArgsConstructor;

// REST Controller for managing vote-related endpoints
@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {
    // Required dependency
    private final VoteService voteService;

    // Endpoint for creating a new vote
    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        // Delegates the save operation to the service layer
        voteService.vote(voteDto);

        // Returns HTTP 201 (Created) response with no body
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
