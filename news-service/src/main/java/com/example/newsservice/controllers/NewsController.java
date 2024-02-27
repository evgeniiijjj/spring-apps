package com.example.newsservice.controllers;

import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.services.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService service;

    @GetMapping("api/news")
    private ResponseEntity<List<NewsDto>> getAllNews(@RequestParam int pageNumber, @RequestParam int pageSize,
                                                                    @RequestParam String userEmail, @RequestParam Long categoryId) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNumber, pageSize), userEmail, categoryId));
    }

    @PostMapping("api/news/news")
    private ResponseEntity<NewsDto> createNews(@RequestBody @Valid NewsDto newsDto) {
        return ResponseEntity.ok(service.createOrUpdate(newsDto));
    }

    @PutMapping("api/news/news")
    private ResponseEntity<NewsDto> updateNews(@RequestBody @Valid NewsDto newsDto) {
        return ResponseEntity.ok(service.createOrUpdate(newsDto));
    }

    @DeleteMapping("api/news/news/{id}")
    private ResponseEntity<Object> deleteNews(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok(MessageFormat.format("id: {0}", id));
    }
}
