package com.example.bookingservice.controller;

import com.example.bookingservice.dto.AllElementsResult;
import com.example.bookingservice.dto.HotelCriteria;
import com.example.bookingservice.dto.HotelDto;
import com.example.bookingservice.dto.HotelDtoForChangeRating;
import com.example.bookingservice.dto.HotelDtoWithRating;
import com.example.bookingservice.service.HotelService;
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

@RequiredArgsConstructor
@RestController
public class HotelController {

    private final HotelService service;

    @GetMapping("/api/hotels")
    public AllElementsResult<HotelDtoWithRating> getHotelsByCriteria(@RequestParam Integer pageNumber,
                                                                                     @RequestParam Integer pageSize,
                                                                                     @RequestParam(required = false) String name,
                                                                                     @RequestParam(required = false) String adTitle,
                                                                                     @RequestParam(required = false) String city,
                                                                                     @RequestParam(required = false) String address,
                                                                                     @RequestParam(required = false) Integer cityCenterDistance,
                                                                                     @RequestParam(required = false) Float rating,
                                                                                     @RequestParam(required = false) Integer numberOfRatings) {
        return service.findAllByCriteria(
                PageRequest.of(pageNumber, pageSize),
                new HotelCriteria(name, adTitle, city, address, cityCenterDistance, rating, numberOfRatings)
        );
    }

    @GetMapping("/api/hotels/hotel/{id}")
    public HotelDtoWithRating getHotelById(@PathVariable Long id) {
        return service.getById(id);
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
    public Long delete(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @PutMapping("/api/hotels/hotel/rating")
    public ResponseEntity<HotelDtoWithRating> updateHotelRating(@RequestBody HotelDtoForChangeRating hotel) {
        return ResponseEntity.accepted().body(service.updateRating(hotel));
    }
}