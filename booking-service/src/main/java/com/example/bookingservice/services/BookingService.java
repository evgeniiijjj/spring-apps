package com.example.bookingservice.services;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.BookingDto;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    AllElementsResult<BookingDto> getAll(Pageable pageable);
    AllElementsResult<BookingDto> getAllByRoom(Pageable pageable, Long id);
    AllElementsResult<BookingDto> getAllByUserName(Pageable pageable, String userName);
    BookingDto create(BookingDto booking);
}
