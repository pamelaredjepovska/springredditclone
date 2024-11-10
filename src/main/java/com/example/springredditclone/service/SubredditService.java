package com.example.springredditclone.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.mapper.SubredditMapper; 
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

// Service class for managing subreddit operations
@Service
@Slf4j
@AllArgsConstructor
public class SubredditService {
    
    // Object that interacts with the DB
    private final SubredditRepository subredditRepository;
    // Mapper object that convert between DB model and DTO model
    private final SubredditMapper subredditMapper; 
    
    // Save subreddit to DB
    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        // Convert the DTO object so DB model object and save it to the DB
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));

        // Set the record's ID to the DTO
        subredditDto.setId(subreddit.getId());

        // Return the new DTO 
        return subredditDto;
    }

    // Read all subreddits from the DB
    // and map them to DTOs
    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream()
                .map(subredditMapper::mapSubredditDto)
                .collect(toList());
    }

    // Find a specific subreddit by its ID
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID " + id));
        
        // Return mapped DTO 
        return subredditMapper.mapSubredditDto(subreddit);
    }
}
