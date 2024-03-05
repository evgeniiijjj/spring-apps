package com.example.bookingservice.services.impl;

import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.HotelDtoWithRating;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.mappers.HotelMapper;
import com.example.bookingservice.repositories.HotelRepository;
import com.example.bookingservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    @Override
    public List<HotelDtoWithRating> getAll(Pageable page) {
        return mapper.toDtoWithRatingList(repository.findAll(page).getContent());
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

    private void validateId(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format("Hotel with id - {0} not found!", id));
        }
    }
}
