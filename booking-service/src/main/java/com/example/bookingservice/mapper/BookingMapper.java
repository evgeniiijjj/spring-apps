package com.example.bookingservice.mapper;

import com.example.bookingservice.dto.BookingDto;
import com.example.bookingservice.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto toDto(Booking booking);
    @Mapping(target = "room.hotel.rating", ignore = true)
    @Mapping(target = "room.hotel.numberOfRatings", ignore = true)
    @Mapping(target = "room.hotel.rooms", ignore = true)
    @Mapping(target = "room.bookings", ignore = true)
    @Mapping(target = "user.id", ignore = true)
    @Mapping(target = "user.password", ignore = true)
    @Mapping(target = "user.bookings", ignore = true)
    Booking toEntity(BookingDto booking);
    List<BookingDto> toDtoList(List<Booking> bookings);
}
