package com.example.bookingservice.services.impl;

import com.example.bookingservice.dtos.BookingDto;
import com.example.bookingservice.entities.Room;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.mappers.BookingMapper;
import com.example.bookingservice.repositories.BookingRepository;
import com.example.bookingservice.repositories.RoomRepository;
import com.example.bookingservice.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper mapper;

    @Override
    public List<BookingDto> getAll(Pageable pageable) {
        return mapper.toDtoList(bookingRepository.findAll(pageable).getContent());
    }

    @Override
    public List<BookingDto> getAllByRoom(Pageable pageable, Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("Room with id - {0} not found!", id)));
        return mapper.toDtoList(bookingRepository.findAllByRoom(pageable, room).getContent());
    }

    @Override
    public BookingDto create(BookingDto booking) {
        return mapper.toDto(bookingRepository.save(mapper.toEntity(booking)));
    }
}
