package com.example.bookingservice.services;

import com.example.bookingservice.dtos.HotelCriteria;
import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.HotelDtoForChangeRating;
import com.example.bookingservice.dtos.HotelDtoWithRating;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    List<HotelDtoWithRating> getAll(Pageable pageable);
    HotelDtoWithRating getById(Long id);
    HotelDto create(HotelDto hotel);
    HotelDtoWithRating update(HotelDto hotel);
    Long deleteById(Long id);

    HotelDtoWithRating updateRating(HotelDtoForChangeRating hotel);

    List<HotelDtoWithRating> findAllByCriteria(Pageable pageable, HotelCriteria criteria);
}
