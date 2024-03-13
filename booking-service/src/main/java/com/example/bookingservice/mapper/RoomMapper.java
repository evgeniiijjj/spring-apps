package com.example.bookingservice.mapper;

import com.example.bookingservice.dto.BookingDates;
import com.example.bookingservice.dto.RoomDto;
import com.example.bookingservice.dto.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "bookings", source = "bookings", qualifiedBy = BookingDatesMapper.class)
    RoomDto toDto(Room room);
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "hotel.rating", ignore = true)
    @Mapping(target = "hotel.numberOfRatings", ignore = true)
    @Mapping(target = "hotel.rooms", ignore = true)
    Room toEntity(RoomDtoForCreateOrUpdate room);
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "hotel.rating", ignore = true)
    @Mapping(target = "hotel.numberOfRatings", ignore = true)
    @Mapping(target = "hotel.rooms", ignore = true)
    Room toEntity(RoomDto room);
    List<RoomDto> toDtoList(List<Room> rooms);

    @BookingDatesMapper
    static List<BookingDates> mapBookings(List<Booking> bookings) {
        if (bookings == null) {
            return List.of();
        }
        return bookings.stream()
                .filter(booking -> booking.getCheckOut().isAfter(Instant.now()))
                .map(booking -> new BookingDates(booking.getCheckIn(), booking.getCheckOut()))
                .toList();
    }
}
