package com.example.bookingservice.mapper;

import com.example.bookingservice.dto.HotelDto;
import com.example.bookingservice.dto.HotelDtoWithRating;
import com.example.bookingservice.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelDto toDto(Hotel hotel);
    HotelDtoWithRating toDtoWithRating(Hotel hotel);
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    Hotel toEntity(HotelDto hotelDto);
    List<HotelDtoWithRating> toDtoWithRatingList(List<Hotel> hotels);
}
