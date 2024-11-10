package com.example.springredditclone.mapper;

import com.example.springredditclone.dto.SubredditDto;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Mapper interface for subreddits
@Mapper(componentModel = "spring")
public interface SubredditMapper { 
    // Map a subreddit entity to subreddit DTO
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditDto(Subreddit subreddit); 

    // Helper method that counts the number of posts for this subreddit
    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    // Convert subreddit DTO back to subreddit entity
    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
