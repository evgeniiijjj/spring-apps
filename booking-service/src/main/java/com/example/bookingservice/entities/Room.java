package com.example.bookingservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_room")
    private String name;
    private String description;
    private Integer number;
    private Integer price;
    private Integer capacity;
    @JoinColumn(name = "hotel_id")
    @ManyToOne(targetEntity = Hotel.class, cascade = CascadeType.ALL)
    private Hotel hotel;
    private String bookingDates;
}
