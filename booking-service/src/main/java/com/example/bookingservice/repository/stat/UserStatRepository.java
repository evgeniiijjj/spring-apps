package com.example.bookingservice.repository.stat;

import com.example.bookingservice.entity.stat.UserStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatRepository extends MongoRepository<UserStat, Long> {
}
