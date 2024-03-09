package com.example.bookingservice.services.impl;

import com.example.bookingservice.aop.VerifyGetBookingsByUser;
import com.example.bookingservice.dtos.BookingDto;
import com.example.bookingservice.entities.Booking;
import com.example.bookingservice.entities.Room;
import com.example.bookingservice.entities.User;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.mappers.BookingMapper;
import com.example.bookingservice.repositories.BookingRepository;
import com.example.bookingservice.repositories.RoomRepository;
import com.example.bookingservice.services.BookingService;
import com.example.bookingservice.services.UserService;
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
    private final UserService userService;
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

    @VerifyGetBookingsByUser
    @Override
    public List<BookingDto> getAllByUserName(Pageable pageable, String userName) {
        User user = userService.getUserByName(userName);
        return mapper.toDtoList(bookingRepository.findAllByUser(pageable, user).getContent());
    }

    @Override
    public BookingDto create(BookingDto bookingDto) {
        Booking booking = mapper.toEntity(bookingDto);
        booking.getUser().setId(userService.getUserByName(booking.getUser().getUserName()).getId());
        return mapper.toDto(bookingRepository.save(booking));
    }
}
