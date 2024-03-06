package com.example.bookingservice.mappers;

import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.entities.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDto toDto(Room room);
    @Mapping(target = "bookingDates", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Room toEntity(RoomDtoForCreateOrUpdate room);
    List<RoomDto> toDtoList(List<Room> rooms);
}
