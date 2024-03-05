package com.example.bookingservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDto {
    private Long id;
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private String cityCenterDistance;
}
