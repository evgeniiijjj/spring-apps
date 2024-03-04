package com.example.newsservice.controllers;

import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class CommentController {

    private final CommentService service;

    @GetMapping("/api/comments")
    private ResponseEntity<List<CommentDto>> getAllCommentByNewsId(@RequestParam Long newsId, @RequestParam String userEmail) {
        return ResponseEntity.ok(service.getAllByNewsIdOrUserId(newsId, userEmail));
    }

    @PostMapping("/api/comments/comment")
    private ResponseEntity<CommentDto> createComment(@RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.ok(service.createOrUpdate(commentDto));
    }

    @PutMapping("/api/comments/comment")
    private ResponseEntity<CommentDto> updateComment(@RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.ok(service.createOrUpdate(commentDto));
    }

    @DeleteMapping("api/comments/comment/{id}")
    private ResponseEntity<Object> deleteComment(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok(MessageFormat.format("id: {0}  ", id));
    }
}
