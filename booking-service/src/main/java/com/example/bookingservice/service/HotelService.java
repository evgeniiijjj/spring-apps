package com.example.bookingservice.service;

import com.example.bookingservice.dto.AllElementsResult;
import com.example.bookingservice.dto.HotelCriteria;
import com.example.bookingservice.dto.HotelDto;
import com.example.bookingservice.dto.HotelDtoForChangeRating;
import com.example.bookingservice.dto.HotelDtoWithRating;
import com.example.bookingservice.entity.Hotel;
import com.example.bookingservice.exception.NotFoundException;
import com.example.bookingservice.mapper.HotelMapper;
import com.example.bookingservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    public AllElementsResult<HotelDtoWithRating> getAll(Pageable pageable) {
        Page<Hotel> result = repository.findAll(pageable);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoWithRatingList(result.getContent()));
    }

    public HotelDtoWithRating getById(Long id) {
        return mapper.toDtoWithRating(
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        MessageFormat.format("Hotel with id - {0} not found!", id)
                                )
                        )
        );
    }

    public HotelDto create(HotelDto hotel) {
        return mapper.toDto(repository.save(mapper.toEntity(hotel)));
    }

    public HotelDtoWithRating update(HotelDto hotel) {
        validateId(hotel.getId());
        return mapper.toDtoWithRating(repository.save(mapper.toEntity(hotel)));
    }

    public Long deleteById(Long id) {
        validateId(id);
        repository.deleteById(id);
        return id;
    }

    public HotelDtoWithRating updateRating(HotelDtoForChangeRating hotelDto) {
        Hotel hotel = repository.findById(hotelDto.getId())
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Hotel with id - {0} not found!", hotelDto.getId())));
        hotel.setRating(Math.round((hotel.getRating() * hotel.getNumberOfRatings() - hotel.getRating() + hotelDto.getNewMark()) / hotel.getNumberOfRatings() * 10) / 10F);
        hotel.setNumberOfRatings(hotel.getNumberOfRatings() + 1);
        return mapper.toDtoWithRating(hotel);
    }

    public AllElementsResult<HotelDtoWithRating> findAllByCriteria(Pageable pageable, HotelCriteria criteria) {
        Page<Hotel> result = repository.findAll(Specification.where(criteria.spec()), pageable);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoWithRatingList(result.getContent()));
    }

    private void validateId(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format("Hotel with id - {0} not found!", id));
        }
    }
}
