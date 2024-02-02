package com.example.newsservice.mappers;

import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.entities.Comment;
import com.example.newsservice.entities.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(target = "commentsNum", source = "comments", qualifiedBy = CommentsNumMapper.class)
    NewsDto toDto(News news);
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "category.newsList", ignore = true)
    @Mapping(target = "user.newsList", ignore = true)
    @Mapping(target = "user.comments", ignore = true)
    News toEntity(NewsDto newsDto);
    List<NewsDto> toDtoList(List<News> newsList);
    @CommentsNumMapper
    static int getCommentsNum(List<Comment> comments) {
        return comments != null ? comments.size() : 0;
    }
}
