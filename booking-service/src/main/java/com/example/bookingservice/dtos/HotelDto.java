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
    @NotBlank(message = "Имя отеля должно быть указано!")
    private String name;
    @NotBlank(message = "Заголовок объявления должен быть указан!")
    private String adTitle;
    @NotBlank(message = "Город - место положения отеля должен быть указан!")
    private String city;
    @NotBlank(message = "Адрес расположения отеля должен быть указан!")
    private String address;
    @NotBlank(message = "Растояние до центра города должно быть указано!")
    private Integer cityCenterDistance;
}
