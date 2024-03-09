package com.example.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDtoWithRating {
    private Long id;
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Integer cityCenterDistance;
    private Float rating;
    private Integer numberOfRatings;
}
