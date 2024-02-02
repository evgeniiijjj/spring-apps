package com.example.newsservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "news_content", nullable = false)
    private String content;
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
    @OneToMany(mappedBy = "news", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}
