package com.example.bookingservice.repositories;

import com.example.bookingservice.entities.Booking;
import com.example.bookingservice.entities.Room;
import com.example.bookingservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByRoom(Pageable page, Room room);

    Page<Booking> findAllByUser(Pageable pageable, User user);
}
