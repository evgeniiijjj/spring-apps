package com.example.bookingservice.entities.stat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.MessageFormat;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "bookings")
public class BookingStat {
    @Id
    @Indexed(unique = true)
    private Long id;
    private Long userId;
    private Instant checkIn;
    private Instant checkOut;

    @Override
    public String toString() {
        return MessageFormat.format(",{0},{1},{2},{3}", id, userId, checkIn, checkOut);
    }
}
