package com.example.newsservice.controllers;

import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.services.CategoryService;
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
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/api/categories")
    private ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/api/categories/category/{id}")
    private ResponseEntity<CategoryDto> getCategoryById(@PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/api/categories/category")
    private ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(service.createOrUpdate(categoryDto));
    }

    @PutMapping("/api/categories/category")
    private ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(service.createOrUpdate(categoryDto));
    }

    @DeleteMapping("/api/categories/category/{id}")
    private ResponseEntity<Object> deleteCategory(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok(MessageFormat.format("id: {0}", id));
    }
}
