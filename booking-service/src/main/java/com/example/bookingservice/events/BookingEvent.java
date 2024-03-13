package com.example.bookingservice.events;

import com.example.bookingservice.entities.stat.BookingStat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingEvent implements Event<BookingStat> {
    private Long bookingId;
    private Long userId;
    private Instant checkIn;
    private Instant checkOut;

    @Override
    public BookingStat getStatEntity() {
        return new BookingStat(bookingId, userId, checkIn, checkOut);
    }
}
