package com.example.bookingservice.repositories.stat;

import com.example.bookingservice.entities.stat.UserStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatRepository extends MongoRepository<UserStat, Long> {
}
