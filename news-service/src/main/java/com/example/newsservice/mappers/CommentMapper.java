package com.example.newsservice.mappers;

import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "news.commentsNum", source = "news.comments", qualifiedBy = CommentsNumMapper.class)
    CommentDto toDto(Comment comment);
    @Mapping(target = "news.category.newsList", ignore = true)
    @Mapping(target = "news.user.newsList", ignore = true)
    @Mapping(target = "news.user.comments", ignore = true)
    @Mapping(target = "news.comments", ignore = true)
    @Mapping(target = "user.newsList", ignore = true)
    @Mapping(target = "user.comments", ignore = true)
    Comment toEntity(CommentDto commentDto);
    List<CommentDto> toDtoList(List<Comment> commentList);
    @CommentsNumMapper
    static int getCommentsNum(List<Comment> comments) {
        return comments != null ? comments.size() : 0;
    }
}
