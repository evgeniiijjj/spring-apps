package com.example.bookingservice.dtos;

import com.example.bookingservice.entities.Room;
import com.example.bookingservice.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private Room room;
    private User user;
    private Instant checkIn;
    private Instant checkOut;
}
