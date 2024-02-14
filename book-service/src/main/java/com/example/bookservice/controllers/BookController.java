package com.example.bookservice.controllers;

import com.example.bookservice.dtos.BookDto;
import com.example.bookservice.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService service;

    @GetMapping("api/books/{category}")
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.getBooksByCategory(category));
    }

    @GetMapping("api/books/book")
    public ResponseEntity<BookDto> getBookByTitleAndAuthor(@RequestParam String title, @RequestParam String author) {
        return ResponseEntity.ok(service.getBookByTitleAndAuthor(title, author));
    }

    @PostMapping("api/books/book")
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto book) {
        return ResponseEntity.ok(service.createBook(book));
    }

    @PutMapping("api/books/book")
    public ResponseEntity<BookDto> updateBook(@RequestBody @Valid BookDto book) {
        return ResponseEntity.ok(service.updateBook(book));
    }

    @DeleteMapping("api/books/book/{id}")
    public ResponseEntity<Integer> deleteBookById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteBook(service.getBookById(id)));
    }
}
