package com.example.bookingservice.controllers;

import com.example.bookingservice.AbstractTest;
import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.RoomDtoForCreateOrUpdate;
import com.example.bookingservice.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomControllerTest extends AbstractTest {

    @Autowired
    protected RoomRepository repository;

    @Test
    public void whenGetRoomByIdByUserWithAdminRole_thenReturnRoom() throws Exception {

        RoomDto expectedResult = new RoomDto(
                1L,
                "first_room",
                "nice room",
                1,
                1000,
                2,
                new HotelDto(
                        1L,
                        "first_hotel",
                        "the best hotel",
                        "Moskow",
                        "ul.Moskowskay, d.23",
                        200
                ),
                List.of()
        );

        RoomDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/rooms/room/1")
                                        .with(httpBasic("John", "john"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                    RoomDto.class
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenGetRoomByIdByUserWithNoAdminRole_thenReturnRoom() throws Exception {

        RoomDto expectedResult = new RoomDto(
                1L,
                "first_room",
                "nice room",
                1,
                1000,
                2,
                new HotelDto(
                        1L,
                        "first_hotel",
                        "the best hotel",
                        "Moskow",
                        "ul.Moskowskay, d.23",
                        200
                ),
                List.of()
        );

        RoomDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/rooms/room/1")
                                        .with(httpBasic("Alex", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                RoomDto.class
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenCreateRoomByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        RoomDtoForCreateOrUpdate newRoom = new RoomDtoForCreateOrUpdate(
                null,
                "fourth_room",
                "nice room",
                4,
                1000,
                2,
                new HotelDto(
                        1L,
                        "first_hotel",
                        "the best hotel",
                        "Moskow",
                        "ul.Moskowskay, d.23",
                        200
                )
        );

        mockMvc
                .perform(
                        post("/api/rooms/room")
                                .with(httpBasic("John", "john"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newRoom))
                )
                .andExpect(status().isCreated());

        assertEquals(10L, repository.count());
    }

    @Test
    public void whenCreateRoomByUserWithRoleNoAdmin_thenReturnForbiddenStatus() throws Exception {

        RoomDtoForCreateOrUpdate newRoom = new RoomDtoForCreateOrUpdate(
                null,
                "fourth_room",
                "nice room",
                4,
                1000,
                2,
                new HotelDto(
                        1L,
                        "first_hotel",
                        "the best hotel",
                        "Moskow",
                        "ul.Moskowskay, d.23",
                        200
                )
        );

        mockMvc
                .perform(
                        post("/api/rooms/room")
                                .with(httpBasic("Alex", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newRoom))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenUpdateRoomByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        RoomDtoForCreateOrUpdate newRoom = new RoomDtoForCreateOrUpdate(
                1L,
                "first_room",
                "very nice room",
                1,
                1000,
                2,
                new HotelDto(
                        1L,
                        "first_hotel",
                        "the best hotel",
                        "Moskow",
                        "ul.Moskowskay, d.23",
                        200
                )
        );

        mockMvc
                .perform(
                        put("/api/rooms/room")
                                .with(httpBasic("John", "john"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newRoom))
                )
                .andExpect(status().isAccepted());
    }

    @Test
    public void whenUpdateRoomByUserWithRoleNoAdmin_thenReturnForbiddenStatus() throws Exception {

        RoomDtoForCreateOrUpdate newRoom = new RoomDtoForCreateOrUpdate(
                1L,
                "first_room",
                "very nice room",
                1,
                1000,
                2,
                new HotelDto(
                        1L,
                        "first_hotel",
                        "the best hotel",
                        "Moskow",
                        "ul.Moskowskay, d.23",
                        200
                )
        );

        mockMvc
                .perform(
                        post("/api/rooms/room")
                                .with(httpBasic("Alex", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(newRoom))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteRoomByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/rooms/room/1")
                                .with(httpBasic("John", "john"))
                )
                .andExpect(status().isOk());

        assertEquals(8L, repository.count());
    }

    @Test
    public void whenDeleteRoomByUserWithRoleNoAdmin_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/rooms/room/1")
                                .with(httpBasic("Bill", "bill"))
                )
                .andExpect(status().isForbidden());
    }
}
