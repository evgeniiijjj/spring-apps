package com.example.bookingservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "room_id")
    @ManyToOne(targetEntity = Room.class, cascade = CascadeType.ALL)
    private Room room;
    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;
    @Column(name = "check_in")
    private Instant checkIn;
    @Column(name = "check_out")
    private Instant checkOut;
}
