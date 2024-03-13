package com.example.bookingservice.services.impl;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.RoomCriteria;
import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.entities.Room;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.mappers.RoomMapper;
import com.example.bookingservice.repositories.RoomRepository;
import com.example.bookingservice.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    @Override
    public RoomDto getById(Long id) {
        return mapper.toDto(getRoomById(id));
    }

    @Override
    public RoomDto create(RoomDtoForCreateOrUpdate room) {
        return mapper.toDto(repository.save(mapper.toEntity(room)));
    }

    @Override
    public RoomDto update(RoomDtoForCreateOrUpdate roomDto) {
        Room room = mapper.toEntity(roomDto);
        room.setBookings(getRoomById(roomDto.getId()).getBookings());
        return mapper.toDto(repository.save(room));
    }

    @Override
    public Long deleteById(Long id) {
        repository.delete(getRoomById(id));
        return id;
    }

    @Override
    public AllElementsResult<RoomDto> findAllByCriteria(Pageable pageable, RoomCriteria criteria) {
        Page<Room> result = repository.findAll(Specification.where(criteria.spec()), pageable);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoList(result.getContent()));
    }

    private Room getRoomById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                MessageFormat
                                        .format("Room with id - {0} not found!", id)
                        )
                );
    }
}
