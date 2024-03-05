package com.example.bookingservice.mappers;

import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.HotelDtoWithRating;
import com.example.bookingservice.entities.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelDto toDto(Hotel hotel);
    HotelDtoWithRating toDtoWithRating(Hotel hotel);
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    Hotel toEntity(HotelDto hotelDto);
    List<HotelDtoWithRating> toDtoWithRatingList(List<Hotel> hotels);
}
