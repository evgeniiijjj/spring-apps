package com.example.bookingservice.services.impl;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.HotelCriteria;
import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.HotelDtoForChangeRating;
import com.example.bookingservice.dtos.HotelDtoWithRating;
import com.example.bookingservice.entities.Hotel;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.mappers.HotelMapper;
import com.example.bookingservice.repositories.HotelRepository;
import com.example.bookingservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    @Override
    public AllElementsResult<HotelDtoWithRating> getAll(Pageable pageable) {
        Page<Hotel> result = repository.findAll(pageable);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoWithRatingList(result.getContent()));
    }

    @Override
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

    @Override
    public HotelDto create(HotelDto hotel) {
        return mapper.toDto(repository.save(mapper.toEntity(hotel)));
    }

    @Override
    public HotelDtoWithRating update(HotelDto hotel) {
        validateId(hotel.getId());
        return mapper.toDtoWithRating(repository.save(mapper.toEntity(hotel)));
    }

    @Override
    public Long deleteById(Long id) {
        validateId(id);
        repository.deleteById(id);
        return id;
    }

    @Override
    public HotelDtoWithRating updateRating(HotelDtoForChangeRating hotelDto) {
        Hotel hotel = repository.findById(hotelDto.getId())
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Hotel with id - {0} not found!", hotelDto.getId())));
        hotel.setRating(Math.round((hotel.getRating() * hotel.getNumberOfRatings() - hotel.getRating() + hotelDto.getNewMark()) / hotel.getNumberOfRatings() * 10) / 10F);
        hotel.setNumberOfRatings(hotel.getNumberOfRatings() + 1);
        return mapper.toDtoWithRating(hotel);
    }

    @Override
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
