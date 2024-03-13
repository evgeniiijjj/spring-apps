package com.example.bookingservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDtoForCreateOrUpdate {
    private Long id;
    @NotBlank(message = "Room name must be specified!")
    private String name;
    @NotBlank(message = "Room description must be specified!")
    private String description;
    @NotBlank(message = "Room number must be specified!")
    private Integer number;
    @NotBlank(message = "Room price must be specified!")
    private Integer price;
    @NotBlank(message = "Room capacity must be specified!")
    private Integer capacity;
    @NotNull(message = "Hotel must be specified!")
    private HotelDto hotel;
}
