package com.example.bookingservice.services;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.HotelCriteria;
import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.HotelDtoForChangeRating;
import com.example.bookingservice.dtos.HotelDtoWithRating;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    AllElementsResult<HotelDtoWithRating> getAll(Pageable pageable);

    HotelDtoWithRating getById(Long id);

    HotelDto create(HotelDto hotel);

    HotelDtoWithRating update(HotelDto hotel);

    Long deleteById(Long id);

    HotelDtoWithRating updateRating(HotelDtoForChangeRating hotel);

    AllElementsResult<HotelDtoWithRating> findAllByCriteria(Pageable pageable, HotelCriteria criteria);
}
