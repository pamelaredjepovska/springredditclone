package com.example.springredditclone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO that encapsultes the subreddit's information
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
    private Long id;

    @NotBlank(message = "Community name is required.")
    private String name;

    @NotBlank(message = "Description is required.")
    private String description;
    
    private Integer numberOfPosts;
}
