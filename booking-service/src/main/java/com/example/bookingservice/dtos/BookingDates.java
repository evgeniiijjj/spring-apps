package com.example.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDates {
    private Instant checkIn;
    private Instant checkOut;
}
