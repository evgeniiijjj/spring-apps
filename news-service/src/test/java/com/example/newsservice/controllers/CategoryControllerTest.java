package com.example.newsservice.controllers;

import com.example.newsservice.AbstractTest;
import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.repositories.CategoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends AbstractTest {

    @Autowired
    protected CategoryRepository repository;

    @Test
    public void whenGetCategories_thenReturnCategoryList() throws Exception {

        List<CategoryDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/categories?pageNumber=0&pageSize=10")
                                        .with(httpBasic("alex@gmail.com", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                    new TypeReference<>() {}
        );

        assertEquals(4, actualResult.size());
    }

    @Test
    public void whenGetCategoryById_thenReturnCategory() throws Exception {

        CategoryDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/categories/category/1")
                                        .with(httpBasic("alex@gmail.com", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CategoryDto.class
        );

        assertEquals(new CategoryDto(1L, "Internet"), actualResult);
    }

    @Test
    public void whenCreateCategoryByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        CategoryDto newCategory = new CategoryDto(null, "Games");

        mockMvc
                .perform(
                        post("/api/categories/category")
                                .with(httpBasic("john@gmail.com", "john"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newCategory))
                )
                .andExpect(status().isOk());

        assertEquals(5L, repository.count());
    }

    @Test
    public void whenCreateCategoryByUserWithRoleModerator_thenReturnOkStatus() throws Exception {

        CategoryDto newCategory = new CategoryDto(null, "Games");

        mockMvc
                .perform(
                        post("/api/categories/category")
                                .with(httpBasic("nick@gmail.com", "nick"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newCategory))
                )
                .andExpect(status().isOk());

        assertEquals(5L, repository.count());
    }

    @Test
    public void whenCreateCategoryByUserWithRoleUser_thenReturnForbiddenStatus() throws Exception {

        CategoryDto newCategory = new CategoryDto(null, "Games");

        mockMvc
                .perform(
                        post("/api/categories/category")
                                .with(httpBasic("bill@gmail.com", "bill"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newCategory))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenUpdateCategoryByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        CategoryDto category = new CategoryDto(1L, "Internet");

        CategoryDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                put("/api/categories/category")
                                        .with(httpBasic("john@gmail.com", "john"))
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(category))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CategoryDto.class
        );

        assertEquals(category, actualResult);
    }

    @Test
    public void whenUpdateCategoryByUserWithRoleModerator_thenReturnOkStatus() throws Exception {

        CategoryDto category = new CategoryDto(1L, "Internet");

        CategoryDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                put("/api/categories/category")
                                        .with(httpBasic("nick@gmail.com", "nick"))
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(category))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CategoryDto.class
        );

        assertEquals(category, actualResult);
    }

    @Test
    public void whenUpdateCategoryByUserWithRoleUser_thenReturnForbiddenStatus() throws Exception {

        CategoryDto category = new CategoryDto(1L, "Internet");

        mockMvc
                .perform(
                        post("/api/categories/category")
                                .with(httpBasic("bill@gmail.com", "bill"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteCategoryByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/categories/category/1")
                                .with(httpBasic("john@gmail.com", "john"))
                )
                .andExpect(status().isOk());

        assertEquals(3L, repository.count());
    }

    @Test
    public void whenDeleteCategoryByUserWithRoleModerator_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/categories/category/1")
                                .with(httpBasic("nick@gmail.com", "nick"))
                )
                .andExpect(status().isOk());

        assertEquals(3L, repository.count());
    }

    @Test
    public void whenDeleteCategoryByUserWithRoleUser_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        post("/api/categories/category/1")
                                .with(httpBasic("bill@gmail.com", "bill"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteCategoryWithNotPresentId_thenReturnNotFoundStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/categories/category/10")
                                .with(httpBasic("nick@gmail.com", "nick"))
                )
                .andExpect(status().isNotFound());
    }
}
