package com.example.bookingservice.controllers;

import com.example.bookingservice.AbstractTest;
import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.BookingDto;
import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.dtos.RoomDto;
import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.enums.RoleType;
import com.example.bookingservice.repositories.BookingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookingControllerTest extends AbstractTest {

    @Autowired
    protected BookingRepository repository;

    @Test
    public void whenGetBookingsByUserWithAdminRole_thenReturnBookingList() throws Exception {

        List<BookingDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/bookings?pageNumber=0&pageSize=10")
                                        .with(httpBasic("John", "john"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                    new TypeReference<AllElementsResult<BookingDto>>() {}
        ).getElements();

        assertEquals(9, actualResult.size());
    }

    @Test
    public void whenGetBookingsByUserWithNoAdminRole_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        get("/api/bookings?pageNumber=0&pageSize=10")
                                .with(httpBasic("Alex", "alex"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenGetBookingsByRoomIdByUserWithAdminRole_thenReturnBookingList() throws Exception {

        List<BookingDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/bookings/room/1?pageNumber=0&pageSize=10")
                                        .with(httpBasic("John", "john"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<AllElementsResult<BookingDto>>() {}
        ).getElements();

        assertEquals(1, actualResult.size());
    }

    @Test
    public void whenGetBookingsByRoomIdByUserWithNoAdminRole_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        get("/api/bookings/room/1?pageNumber=0&pageSize=10")
                                .with(httpBasic("Alex", "alex"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenCreateBookingByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        BookingDto newBooking = new BookingDto(
                null,
                new RoomDto(
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
                ),
                new UserDto(
                        "John",
                        "john@gmail.com",
                        RoleType.ROLE_ADMIN
                ),
                Instant.now(),
                Instant.now().plus(10, ChronoUnit.DAYS)
        );

        mockMvc
                .perform(
                        post("/api/bookings/booking")
                                .with(httpBasic("John", "john"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newBooking))
                )
                .andExpect(status().isCreated());

        assertEquals(10L, repository.count());
    }

    @Test
    public void whenCreateBookingByUserWithRoleNoAdmin_thenReturnOkStatus() throws Exception {

        BookingDto newBooking = new BookingDto(
                null,
                new RoomDto(
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
                ),
                new UserDto(
                        "John",
                        "john@gmail.com",
                        RoleType.ROLE_ADMIN
                ),
                Instant.now(),
                Instant.now().plus(10, ChronoUnit.DAYS)
        );

        mockMvc
                .perform(
                        post("/api/bookings/booking")
                                .with(httpBasic("Alex", "alex"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newBooking))
                )
                .andExpect(status().isCreated());

        assertEquals(10L, repository.count());
    }

    @Test
    public void whenGetBookingsByUserNameByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        List<BookingDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/bookings/user/Alex?pageNumber=0&pageSize=10")
                                        .with(httpBasic("John", "john"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<AllElementsResult<BookingDto>>() {}
        ).getElements();

        assertEquals(2, actualResult.size());
    }

    @Test
    public void whenGetUserBookingsByUserNameByUserWithRoleNoAdmin_thenReturnOkStatus() throws Exception {

        List<BookingDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/bookings/user/Alex?pageNumber=0&pageSize=10")
                                        .with(httpBasic("Alex", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<AllElementsResult<BookingDto>>() {}
        ).getElements();

        assertEquals(2, actualResult.size());
    }

    @Test
    public void whenGetOtherUserBookingsByUserNameByUserWithRoleNoAdmin_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        get("/api/bookings/user/Alex?pageNumber=0&pageSize=10")
                                .with(httpBasic("Nick", "nick"))
                )
                .andExpect(status().isForbidden());
    }
}
