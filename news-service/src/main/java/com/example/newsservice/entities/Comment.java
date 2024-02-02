package com.example.newsservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
    @JoinColumn(name = "news_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private News news;
}
