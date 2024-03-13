package com.example.bookingservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {
    private Long id;
    @NotBlank(message = "Hotel name must be specified!")
    private String name;
    @NotBlank(message = "Ad title must be specified!")
    private String adTitle;
    @NotBlank(message = "City must be specified!")
    private String city;
    @NotBlank(message = "Address must be specified!")
    private String address;
    @NotBlank(message = "City center destination must be specified!")
    private Integer cityCenterDistance;
}
