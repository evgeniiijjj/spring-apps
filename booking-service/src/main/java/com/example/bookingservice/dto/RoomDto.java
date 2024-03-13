package com.example.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Integer price;
    private Integer capacity;
    private HotelDto hotel;
    private List<BookingDates> bookings;
}
