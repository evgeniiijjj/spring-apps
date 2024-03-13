package com.example.bookingservice.controllers;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.RoomCriteria;
import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService service;

    @GetMapping("/api/rooms/room/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/api/rooms")
    public ResponseEntity<AllElementsResult<RoomDto>> getRoomsByCriteria(@RequestParam Integer pageNumber,
                                                                         @RequestParam Integer pageSize,
                                                                         @RequestParam(required = false) String name,
                                                                         @RequestParam(required = false) String description,
                                                                         @RequestParam(required = false) Integer minPrice,
                                                                         @RequestParam(required = false) Integer maxPrice,
                                                                         @RequestParam(required = false) Integer capacity,
                                                                         @RequestParam(required = false) Instant checkIn,
                                                                         @RequestParam(required = false) Instant checkOut,
                                                                         @RequestParam(required = false) Long hotelId) {
        return ResponseEntity.ok(
                service.findAllByCriteria(
                        PageRequest.of(pageNumber, pageSize),
                        new RoomCriteria(
                            name, description, minPrice, maxPrice, capacity, checkIn, checkOut, hotelId
                        )
                )
        );
    }

    @PostMapping("/api/rooms/room")
    public ResponseEntity<RoomDto> create(@RequestBody RoomDtoForCreateOrUpdate room) {
        RoomDto result = service.create(room);
        return ResponseEntity.created(URI.create("api/rooms/room/" + result.getId())).body(result);
    }

    @PutMapping("/api/rooms/room")
    public ResponseEntity<RoomDto> update(@RequestBody RoomDtoForCreateOrUpdate hotel) {
        return ResponseEntity.accepted().body(service.update(hotel));
    }

    @DeleteMapping("/api/rooms/room/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}