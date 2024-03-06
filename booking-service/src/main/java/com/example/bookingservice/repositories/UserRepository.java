package com.example.bookingservice.repositories;

import com.example.bookingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String name);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    void deleteByUserName(String name);
}
