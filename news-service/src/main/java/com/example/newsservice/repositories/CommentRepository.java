package com.example.newsservice.repositories;

import com.example.newsservice.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(long id);
    List<Comment> findByNewsId(long id);
}
