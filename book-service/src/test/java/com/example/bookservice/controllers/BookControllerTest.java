package com.example.bookservice.controllers;

import com.example.bookservice.AbstractTest;
import com.example.bookservice.dtos.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends AbstractTest {

    @Test
    public void whenGetBooksByCategory_thenReturnBooksList() throws Exception {

        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        String actualResponse = mockMvc.perform(get("/api/books/SCI_FI"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        String expectedResponse = objectMapper.writeValueAsString(bookService.getBooksByCategory("SCI_FI"));
        assertFalse(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetBooksByNonExistentCategory_thenReturnErrorMessage() throws Exception {

        String actualResponse = mockMvc.perform(get("/api/books/SCI_FII"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        String expectedResponse = "Категория - SCI_FII отсутствует.";
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetBooksByTitleAndAuthor_thenReturnBook() throws Exception {

        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        String actualResponse = mockMvc.perform(get("/api/books/book?title=Старик и море&author=Хемингуэй"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        String expectedResponse = objectMapper.writeValueAsString(new BookDto(1, "Старик и море", "Хемингуэй", "NOVEL"));
        assertFalse(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetBooksByTitleAndAuthorIfNotFound_thenReturnErrorMessage() throws Exception {

        String actualResponse = mockMvc.perform(get("/api/books/book?title=Старик и мореее&author=Хемингуэй"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        String expectedResponse = "Книга с названием - Старик и мореее автора - Хемингуэй отсутствует.";
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenPostCreateBook_thenCreateBookAndEvictCache() throws Exception {

        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(5, bookRepository.count());

        mockMvc.perform(get("/api/books/SCI_FI"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        assertFalse(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());

        String actualResponse = mockMvc.perform(post("/api/books/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new BookDto(null, "Вишневый сад", "А. Чехов", "NOVEL"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        String expectedResponse = objectMapper.writeValueAsString(new BookDto(6, "Вишневый сад", "А. Чехов", "NOVEL"));
        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(6, bookRepository.count());
        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenPostCreateBookWithEmptyFields_thenReturnErrorMessage() throws Exception {

        List<String> actualResponse = Arrays.asList(mockMvc.perform(post("/api/books/book")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new BookDto(null, "", "", ""))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8)
                .split("\n"));
        actualResponse.sort(String::compareTo);

        List<String> expectedResponse = List.of("Название книги не должно быть пустым!",
                "Поле автор не должно быть пустым!",
                "Поле категория не должно быть пустым!");
        assertEquals(5, bookRepository.count());
        assertArrayEquals(expectedResponse.toArray(), actualResponse.toArray());
    }

    @Test
    public void whenPostUpdateBook_thenUpdateBookAndEvictCache() throws Exception {

        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(5, bookRepository.count());

        mockMvc.perform(get("/api/books/SCI_FI"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        assertFalse(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());

        String actualResponse = mockMvc.perform(post("/api/books/book")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new BookDto(5, "Час быка", "И. А. Ефремов", "NOVEL"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        String expectedResponse = objectMapper.writeValueAsString(new BookDto(5, "Час быка", "И. А. Ефремов", "NOVEL"));
        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(5, bookRepository.count());
        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteBookById_thenDeleteBookAndEvictCache() throws Exception {

        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(5, bookRepository.count());

        mockMvc.perform(get("/api/books/SCI_FI"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        assertFalse(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());

        String actualResponse = mockMvc.perform(delete("/api/books/book/5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(5)))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        assertTrue(Objects.requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(4, bookRepository.count());
        assertJsonEquals(5, actualResponse);
    }

    @Test
    public void whenDeleteBookByNotPresentId_thenReturnErrorMessage() throws Exception {

        String actualResponse = mockMvc.perform(delete("/api/books/book/15")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(15)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        String expectedResponse = "Книга с id 15 отсутствует.";
        assertEquals(5, bookRepository.count());
        assertEquals(expectedResponse, actualResponse);
    }
}
