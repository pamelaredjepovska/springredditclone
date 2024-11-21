package com.example.springredditclone.dto;

import java.time.Instant;
import jakarta.validation.constraints.NotBlank; // Ensures the field is not blank during validation
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for transferring comment data between layers
@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class CommentDto {
    private Long id; // Unique identifier for the comment
    private Long postId; // The ID of the post this comment belongs to
    private Instant createdDate; // Timestamp when the comment was created
    @NotBlank // Ensures the text field is not null or empty
    private String text; // Content of the comment
    private String userName; // The username of the user who made the comment
}
