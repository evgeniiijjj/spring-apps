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
    @NotBlank(message = "Имя комнаты должно быть указано!")
    private String name;
    @NotBlank(message = "Описание комнаты должно быть указано!")
    private String description;
    @NotBlank(message = "Номер комнаты должен быть указан!")
    private Integer number;
    @NotBlank(message = "Цена комнаты должна быть указана!")
    private Integer price;
    @NotBlank(message = "Вместимость комнаты должна быть указана!")
    private Integer capacity;
    @NotNull(message = "Отель должен быть указан!")
    private HotelDto hotel;
}
