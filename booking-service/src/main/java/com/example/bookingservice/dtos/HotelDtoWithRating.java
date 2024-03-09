package com.example.bookingservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDtoWithRating {
    private Long id;
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Integer cityCenterDistance;
    private Integer rating;
    private Integer numberOfRatings;
}
