package com.example.bookingservice.services;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.RoomCriteria;
import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;
import org.springframework.data.domain.Pageable;

public interface RoomService {

    RoomDto getById(Long id);

    RoomDto create(RoomDtoForCreateOrUpdate room);

    RoomDto update(RoomDtoForCreateOrUpdate room);

    Long deleteById(Long id);

    AllElementsResult<RoomDto> findAllByCriteria(Pageable pageable, RoomCriteria criteria);
}
