package com.example.bookingservice.repository;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Room;
import com.example.bookingservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    Page<Booking> findAllByRoom(Pageable page, Room room);

    Page<Booking> findAllByUser(Pageable pageable, User user);
}
