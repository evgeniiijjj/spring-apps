package com.example.bookingservice.repository.stat;

import com.example.bookingservice.entity.stat.BookingStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingStatRepository extends MongoRepository<BookingStat, Long> {
}
