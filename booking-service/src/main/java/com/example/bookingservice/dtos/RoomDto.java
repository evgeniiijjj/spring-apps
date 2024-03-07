package com.example.bookingservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Integer price;
    private Integer capacity;
    private HotelDto hotel;
    private List<BookingDto> bookings;
}
