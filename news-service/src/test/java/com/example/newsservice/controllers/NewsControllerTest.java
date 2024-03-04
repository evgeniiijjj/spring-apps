package com.example.newsservice.controllers;

import com.example.newsservice.AbstractTest;
import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.repositories.NewsRepository;
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

public class NewsControllerTest extends AbstractTest {

    @Autowired
    protected NewsRepository repository;

    @Test
    public void whenGetNewsByUserEmailAndCategoryId_thenReturnNewsList() throws Exception {

        List<NewsDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/news?pageNumber=0&pageSize=10&userEmail=john@gmail.com&categoryId=1")
                                        .with(httpBasic("alex@gmail.com", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                    new TypeReference<>() {}
        );

        assertEquals(3, actualResult.size());
    }

    @Test
    public void whenCreateNews_thenReturnOkStatus() throws Exception {

        NewsDto newNews = new NewsDto(null, "news content", new CategoryDto(4L, "Science"), new UserDto("", "", "alfred@gmail.com"), 0);

        mockMvc
                .perform(
                        post("/api/news/news")
                                .with(httpBasic("alfred@gmail.com", "alfred"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newNews))
                )
                .andExpect(status().isOk());

        assertEquals(11L, repository.count());
    }

    @Test
    public void whenUpdateNewsByNewsAuthor_thenReturnOkStatus() throws Exception {

        NewsDto news = new NewsDto(8L, "new news content", new CategoryDto(4L, "Science"), new UserDto("Frank", "Sinatra", "frank@gmail.com"), 0);

        NewsDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                put("/api/news/news")
                                        .with(httpBasic("frank@gmail.com", "frank"))
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(news))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                NewsDto.class
        );

        assertEquals(news, actualResult);
    }

    @Test
    public void whenUpdateNewsByNoAuthorUser_thenReturnForbiddenStatus() throws Exception {

        NewsDto news = new NewsDto(8L, "new news content", new CategoryDto(4L, "Science"), new UserDto("Frank", "Sinatra", "frank@gmail.com"), 2);

        mockMvc
                .perform(
                        put("/api/news/news")
                                .with(httpBasic("alex@gmail.com", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(news))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteNewsByNewsAuthorUser_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/news/news/4")
                                .with(httpBasic("nick@gmail.com", "nick"))
                )
                .andExpect(status().isOk());

        assertEquals(9L, repository.count());
    }

    @Test
    public void whenDeleteNewsByNoAuthorUser_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/news/news/7")
                                .with(httpBasic("alfred@gmail.com", "alfred"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteNewsByAdminUser_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/news/news/7")
                                .with(httpBasic("john@gmail.com", "john"))
                )
                .andExpect(status().isOk());

        assertEquals(9L, repository.count());
    }

    @Test
    public void whenDeleteNewsByModeratorUser_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/news/news/7")
                                .with(httpBasic("nick@gmail.com", "nick"))
                )
                .andExpect(status().isOk());

        assertEquals(9L, repository.count());
    }
}
