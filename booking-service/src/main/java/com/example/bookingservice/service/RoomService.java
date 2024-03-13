package com.example.bookingservice.service;

import com.example.bookingservice.dto.AllElementsResult;
import com.example.bookingservice.dto.RoomCriteria;
import com.example.bookingservice.dto.RoomDto;
import com.example.bookingservice.dto.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.entity.Room;
import com.example.bookingservice.exception.NotFoundException;
import com.example.bookingservice.mapper.RoomMapper;
import com.example.bookingservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    public RoomDto getById(Long id) {
        return mapper.toDto(getRoomById(id));
    }

    public RoomDto create(RoomDtoForCreateOrUpdate room) {
        return mapper.toDto(repository.save(mapper.toEntity(room)));
    }

    public RoomDto update(RoomDtoForCreateOrUpdate roomDto) {
        Room room = mapper.toEntity(roomDto);
        room.setBookings(getRoomById(roomDto.getId()).getBookings());
        return mapper.toDto(repository.save(room));
    }

    public Long deleteById(Long id) {
        repository.delete(getRoomById(id));
        return id;
    }

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
