package com.example.bookingservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Integer price;
    private Integer capacity;
    private String bookingDates;
    private HotelDto hotel;
}
