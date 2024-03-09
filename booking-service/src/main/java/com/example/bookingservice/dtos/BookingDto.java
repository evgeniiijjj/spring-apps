package com.example.bookingservice.dtos;

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
    @NotNull(message = "Комната должна быть указана!")
    private RoomDto room;
    @NotNull(message = "Комната должна быть указана!")
    private UserDto user;
    @NotNull(message = "Дата и время бронирования должны быть указаны!")
    private Instant checkIn;
    @NotNull(message = "Дата и время окончания бронирования должны быть указаны!")
    private Instant checkOut;
}
