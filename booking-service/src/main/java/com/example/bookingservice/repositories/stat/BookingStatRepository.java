package com.example.bookingservice.repositories.stat;

import com.example.bookingservice.entities.stat.BookingStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatRepository extends MongoRepository<BookingStat, Long> {
}
