package com.example.springredditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springredditclone.dto.CommentDto;
import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;

// MapStruct interface for mapping between Comment and CommentDto objects
@Mapper(componentModel = "spring") 
public interface CommentMapper {

    // Maps a CommentDto object to a Comment object
    @Mapping(target = "id", ignore = true) // The Comment's id is ignored as it will be auto-generated
    @Mapping(target = "text", source = "commentsDto.text") 
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post") 
    @Mapping(target = "user", source = "user") 
    Comment map(CommentDto commentsDto, Post post, User user);

    // Maps a Comment object to a CommentDto object
    @Mapping(target = "postId", expression = "java(comment.getPost().getId())") 
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}
