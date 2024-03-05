package com.example.bookingservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hotel_name")
    private String name;
    @Column(name = "ad_title")
    private String adTitle;
    private String city;
    private String address;
    @Column(name = "city_center_distance")
    private String cityCenterDistance;
    private Integer rating;
    @Column(name = "number_of_ratings")
    private Integer numberOfRatings;
}
