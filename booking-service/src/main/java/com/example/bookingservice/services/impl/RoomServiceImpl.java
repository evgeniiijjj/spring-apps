package com.example.bookingservice.services.impl;

import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.mappers.RoomMapper;
import com.example.bookingservice.repositories.RoomRepository;
import com.example.bookingservice.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    @Override
    public RoomDto getById(Long id) {
        return mapper.toDto(
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        MessageFormat.format("Room with id - {0} not found!", id)
                                )
                        )
        );
    }

    @Override
    public RoomDto create(RoomDtoForCreateOrUpdate room) {
        return mapper.toDto(repository.save(mapper.toEntity(room)));
    }

    @Override
    public RoomDto update(RoomDtoForCreateOrUpdate room) {
        validateId(room.getId());
        return mapper.toDto(repository.save(mapper.toEntity(room)));
    }

    @Override
    public Long deleteById(Long id) {
        validateId(id);
        repository.deleteById(id);
        return id;
    }

    private void validateId(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(MessageFormat.format("Room with id - {0} not found!", id));
        }
    }
}
