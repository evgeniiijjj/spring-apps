package com.example.bookingservice.services;

import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;

public interface RoomService {
    RoomDto getById(Long id);
    RoomDto create(RoomDtoForCreateOrUpdate room);
    RoomDto update(RoomDtoForCreateOrUpdate room);
    Long deleteById(Long id);
}
