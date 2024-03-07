package com.example.bookingservice.mappers;

import com.example.bookingservice.dtos.BookingDto;
import com.example.bookingservice.entities.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto toDto(Booking booking);
    Booking toEntity(BookingDto booking);
    List<BookingDto> toDtoList(List<Booking> bookings);
}
