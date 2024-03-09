package com.example.bookingservice.controllers;

import com.example.bookingservice.AbstractTest;
import com.example.bookingservice.dtos.HotelDto;
import com.example.bookingservice.repositories.HotelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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

public class HotelControllerTest extends AbstractTest {

    @Autowired
    protected HotelRepository repository;

    @Test
    public void whenGetHotelsByUserWithAdminRole_thenReturnHotelList() throws Exception {

        List<HotelDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/hotels?pageNum=0&pageSize=10")
                                        .with(httpBasic("John", "john"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                    new TypeReference<>() {}
        );

        assertEquals(3, actualResult.size());
    }

    @Test
    public void whenGetHotelsByUserWithNoAdminRole_thenReturnHotelList() throws Exception {

        List<HotelDto> actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/hotels?pageNum=0&pageSize=10")
                                        .with(httpBasic("Alex", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<>() {}
        );

        assertEquals(3, actualResult.size());
    }

    @Test
    public void whenGetHotelByIdByUserWithAdminRole_thenReturnHotel() throws Exception {

        HotelDto expectedResult = new HotelDto(1L, "first_hotel", "the best hotel", "Moskow", "ul.Moskowskay, d.23", 200);

        HotelDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/hotels/hotel/1")
                                        .with(httpBasic("John", "john"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                HotelDto.class
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenGetHotelByIdByUserWithNoAdminRole_thenReturnHotel() throws Exception {

        HotelDto expectedResult = new HotelDto(1L, "first_hotel", "the best hotel", "Moskow", "ul.Moskowskay, d.23", 200);

        HotelDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                get("/api/hotels/hotel/1")
                                        .with(httpBasic("Alex", "alex"))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                HotelDto.class
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenCreateHotelByUserWithAdminRole_thenReturnHotel() throws Exception {

        HotelDto expectedResult = new HotelDto(null, "fourth_hotel", "the best hotel", "Krasnodar", "ul.Moskowskay, d.23", 200);

        HotelDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                post("/api/hotels/hotel")
                                        .with(httpBasic("John", "john"))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(expectedResult))
                        )
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                HotelDto.class
        );

        expectedResult.setId(4L);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenCreateHotelByUserWithNoAdminRole_thenForbiddenStatus() throws Exception {

        HotelDto expectedResult = new HotelDto(null, "fourth_hotel", "the best hotel", "Krasnodar", "ul.Moskowskay, d.23", 200);

            mockMvc
                    .perform(
                            post("/api/hotels/hotel")
                                    .with(httpBasic("Alex", "alex"))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(expectedResult))
                    )
                    .andExpect(status().isForbidden())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
    }

    @Test
    public void whenUpdateHotelByUserWithAdminRole_thenReturnHotel() throws Exception {

        HotelDto expectedResult = new HotelDto(1L, "first_hotel", "the very best hotel", "Moskow", "ul.Moskowskay, d.23", 200);

        HotelDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                put("/api/hotels/hotel")
                                        .with(httpBasic("John", "john"))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(expectedResult))
                        )
                        .andExpect(status().isAccepted())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                HotelDto.class
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenUpdateHotelByUserWithNoAdminRole_thenForbiddenStatus() throws Exception {

        HotelDto expectedResult = new HotelDto(1L, "first_hotel", "the very best hotel", "Moskow", "ul.Moskowskay, d.23", 200);

        mockMvc
                .perform(
                        put("/api/hotels/hotel")
                                .with(httpBasic("Alex", "alex"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(expectedResult))
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void whenDeleteHotelByUserWithAdminRole_thenReturnOkStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/hotels/hotel/1")
                                .with(httpBasic("John", "john"))
                )
                .andExpect(status().isOk());

        assertEquals(2L, repository.count());
    }

    @Test
    public void whenDeleteHotelByUserWithNoAdminRole_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        delete("/api/hotels/hotel/1")
                                .with(httpBasic("Alex", "alex"))
                )
                .andExpect(status().isForbidden());
    }
}
