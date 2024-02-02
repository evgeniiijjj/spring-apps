package com.example.newsservice.repositories;

import com.example.newsservice.entities.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByCategoryId(long id);
    List<News> findByUserId(long id);
    List<News> findByUserIdAndCategoryId(Pageable pageable, long userId, long categoryId);
}
