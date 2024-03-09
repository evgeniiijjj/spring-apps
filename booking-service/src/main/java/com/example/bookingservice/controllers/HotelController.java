package com.example.bookingservice.controllers;

import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.HotelDtoForChangeRating;
import com.example.bookingservice.dtos.HotelDtoWithRating;
import com.example.bookingservice.services.HotelService;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HotelController {

    private final HotelService service;

    @GetMapping("/api/hotels")
    public ResponseEntity<List<HotelDtoWithRating>> getHotels(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNum, pageSize)));
    }

    @GetMapping("/api/hotels/hotel/{id}")
    public ResponseEntity<HotelDtoWithRating> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/api/hotels/hotel")
    public ResponseEntity<HotelDto> create(@RequestBody HotelDto hotel) {
        HotelDto result = service.create(hotel);
        return ResponseEntity.created(URI.create("api/hotels/hotel/" + result.getId())).body(result);
    }

    @PutMapping("/api/hotels/hotel")
    public ResponseEntity<HotelDtoWithRating> update(@RequestBody HotelDto hotel) {
        return ResponseEntity.accepted().body(service.update(hotel));
    }

    @DeleteMapping("/api/hotels/hotel/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PutMapping("/api/hotels/hotel/rating")
    public ResponseEntity<HotelDtoWithRating> updateHotelRating(@RequestBody HotelDtoForChangeRating hotel) {
        return ResponseEntity.accepted().body(service.updateRating(hotel));
    }
}