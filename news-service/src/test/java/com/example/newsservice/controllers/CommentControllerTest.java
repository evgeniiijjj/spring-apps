package com.example.newsservice.controllers;

import com.example.newsservice.AbstractTest;
import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.repositories.CommentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends AbstractTest {

    @Autowired
    protected CommentRepository repository;

    @Test
    public void whenGetCommentsByNewsIdAndUserEmail_thenReturnCommentsList() throws Exception {

        List<CommentDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/comments?newsId=10&userEmail=john@gmail.com")
                                        .with(httpBasic("alex@gmail.com", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                    new TypeReference<>() {}
        );

        assertEquals(2, actualResult.size());
    }

    @Test
    public void whenCreateComments_thenReturnOkStatus() throws Exception {

        CommentDto newComment = new CommentDto(
                null,
                "new comment",
                new UserDto("Alex", "Romanov", "alex@gmail.com"),
                new NewsDto(
                        10L,
                        "Astronomers are puzzled over an enigmatic companion to a pulsar",
                        new CategoryDto(4L, "Science"),
                        new UserDto("Alfred", "Morgenstein", "alfred@gmail.com"),
                        0
                )
        );

        mockMvc
                .perform(
                        post("/api/comments/comment")
                                .with(httpBasic("bill@gmail.com", "bill"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newComment))
                )
                .andExpect(status().isOk());

        assertEquals(12L, repository.count());
    }

    @Test
    public void whenUpdateCommentsByCommentsAuthorUser_thenReturnOkStatus() throws Exception {

        CommentDto comment = new CommentDto(
                11L,
                "update comment",
                new UserDto("Frank", "Sinatra", "frank@gmail.com"),
                new NewsDto(
                        10L,
                        "Astronomers are puzzled over an enigmatic companion to a pulsar",
                        new CategoryDto(4L, "Science"),
                        new UserDto("Alfred", "Morgenstein", "alfred@gmail.com"),
                        2
                )
        );

        CommentDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                put("/api/comments/comment")
                                        .with(httpBasic("frank@gmail.com", "frank"))
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(comment))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CommentDto.class
        );

        assertEquals(comment, actualResult);
    }

    @Test
    public void whenUpdateCommentsByNoAuthorUser_thenReturnForbiddenStatus() throws Exception {

        CommentDto comment = new CommentDto(
                11L,
                "update comment",
                new UserDto("Frank", "Sinatra", "frank@gmail.com"),
                new NewsDto(
                        10L,
                        "Astronomers are puzzled over an enigmatic companion to a pulsar",
                        new CategoryDto(4L, "Science"),
                        new UserDto("Alfred", "Morgenstein", "alfred@gmail.com"),
                        2
                )
        );

        mockMvc
                .perform(
                        put("/api/comments/comment")
                                .with(httpBasic("alex@gmail.com", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(comment))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteNewsByCommentAuthorUser_thenReturnOkStatus() throws Exception {

        UserDto user = new UserDto("Frank", "Sinatra", "frank@gmail.com");

        mockMvc
                .perform(
                        delete("/api/comments/comment/4")
                                .with(httpBasic("frank@gmail.com", "frank"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isOk());

        assertEquals(10L, repository.count());
    }

    @Test
    public void whenDeleteCommentByNoAuthorUser_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/comments/comment/4")
                                .with(httpBasic("alfred@gmail.com", "alfred"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteNewsByAdminUser_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/comments/comment/7")
                                .with(httpBasic("john@gmail.com", "john"))
                )
                .andExpect(status().isOk());

        assertEquals(10L, repository.count());
    }

    @Test
    public void whenDeleteCommentByModeratorUser_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/comments/comment/7")
                                .with(httpBasic("nick@gmail.com", "nick"))
                )
                .andExpect(status().isOk());

        assertEquals(10L, repository.count());
    }
}
