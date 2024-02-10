package com.example.bookservice.repositories;

import com.example.bookservice.entities.Book;
import com.example.bookservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByCategory(Category category);

    Optional<Book> findByTitleAndAuthor(String title, String author);

    int countByCategory(Category category);
}
