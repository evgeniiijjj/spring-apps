package com.example.bookingservice.services;

import com.example.bookingservice.dtos.BookingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    List<BookingDto> getAll(Pageable pageable);
    List<BookingDto> getAllByRoom(Pageable pageable, Long id);
    List<BookingDto> getAllByUserName(Pageable pageable, String userName);
    BookingDto create(BookingDto booking);
}
