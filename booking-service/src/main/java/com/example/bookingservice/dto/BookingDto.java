package com.example.bookingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto {
    private Long id;
    @NotNull(message = "Room must be specified!")
    private RoomDto room;
    @NotNull(message = "User must be specified!")
    private UserDto user;
    @NotNull(message = "Check in date and time must be specified!")
    private Instant checkIn;
    @NotNull(message = "Check out date and time must be specified!")
    private Instant checkOut;
}
