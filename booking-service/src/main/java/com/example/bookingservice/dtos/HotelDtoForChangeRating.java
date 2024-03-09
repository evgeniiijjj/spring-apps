package com.example.bookingservice.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelDtoForChangeRating {
    @NotNull(message = "Hotel id must be specified!")
    private Long id;
    @NotNull(message = "Mark must be specified!")
    @Min(message = "Mark value cannot be less than 1!", value = 1)
    @Max(message = "Mark value cannot be more than 5!", value = 5)
    private Integer newMark;
}
