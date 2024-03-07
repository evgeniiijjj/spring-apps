package com.example.bookingservice.controllers;

import com.example.bookingservice.dtos.BookingDto;
import com.example.bookingservice.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookingController {

    private final BookingService service;

    @GetMapping("api/bookings")
    public ResponseEntity<List<BookingDto>> getAll(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("api/bookings/booking/{roomId}")
    public ResponseEntity<List<BookingDto>> getAllByRoomId(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize,
                                                                @PathVariable Long roomId) {
        return ResponseEntity.ok(service.getAllByRoom(PageRequest.of(pageNumber, pageSize), roomId));
    }

    @PostMapping("api/bookings/booking")
    public ResponseEntity<BookingDto> create(@RequestBody BookingDto booking) {
        BookingDto result = service.create(booking);
        return ResponseEntity.created(URI.create("api/bookings/booking/" + result.getId())).body(result);
    }
}