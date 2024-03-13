package com.example.bookingservice.service;

import com.example.bookingservice.aop.VerifyGetBookingsByUser;
import com.example.bookingservice.dto.AllElementsResult;
import com.example.bookingservice.dto.BookingDto;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.Room;
import com.example.bookingservice.entity.User;
import com.example.bookingservice.event.BookingEvent;
import com.example.bookingservice.event.Event;
import com.example.bookingservice.exception.NotFoundException;
import com.example.bookingservice.mapper.BookingMapper;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserService userService;
    private final BookingMapper mapper;

    @Value("${app.kafka.bookingTopic}")
    private String topicName;
    private final KafkaTemplate<String, Event<?>> template;

    public AllElementsResult<BookingDto> getAll(Pageable pageable) {
        Page<Booking> result = bookingRepository.findAll(pageable);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoList(result.getContent()));
    }

    public AllElementsResult<BookingDto> getAllByRoom(Pageable pageable, Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("Room with id - {0} not found!", id)));
        Page<Booking> result = bookingRepository.findAllByRoom(pageable, room);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoList(result.getContent()));
    }

    @VerifyGetBookingsByUser
    public AllElementsResult<BookingDto> getAllByUserName(Pageable pageable, String userName) {
        User user = userService.getUserByName(userName);
        Page<Booking> result = bookingRepository.findAllByUser(pageable, user);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoList(result.getContent()));
    }

    public BookingDto create(BookingDto bookingDto) {
        Booking booking = mapper.toEntity(bookingDto);
        booking.getUser().setId(userService.getUserByName(booking.getUser().getUserName()).getId());
        booking = bookingRepository.save(booking);
        template.send(topicName, new BookingEvent(booking.getId(), booking.getUser().getId(), booking.getCheckIn(), booking.getCheckOut()));
        return mapper.toDto(booking);
    }
}
