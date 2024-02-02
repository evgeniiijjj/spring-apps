package com.example.newsservice.repositories;

import com.example.newsservice.entities.News;
import com.example.newsservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
