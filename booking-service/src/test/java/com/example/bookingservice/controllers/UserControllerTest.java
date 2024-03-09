package com.example.bookingservice.controllers;


import com.example.bookingservice.AbstractTest;
import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.dtos.UserDtoForCreate;
import com.example.bookingservice.enums.RoleType;
import com.example.bookingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void whenGetUserByNameByUserWithRoleAdmin_thenReturnUser() throws Exception {

        UserDto expectedResult = new UserDto("Bill", "bill@gmail.com", RoleType.ROLE_USER);

        UserDto actualResult = objectMapper.readValue(
            mockMvc
                    .perform(
                            get("/api/users/user/Bill")
                                    .with(httpBasic("John", "john"))
                    )
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                UserDto.class
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenGetUserByNameByOtherUserWithNoAdminRole_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        get("/api/users/user/Bill")
                                .with(httpBasic("Alex", "alex"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenCreateUser_thenReturnOkStatus() throws Exception {

        UserDtoForCreate newUser = new UserDtoForCreate("Bob", "bob", "bob@gmail.com", RoleType.ROLE_USER);
        UserDto expectedResult = new UserDto("Bob", "bob@gmail.com", RoleType.ROLE_USER);

        UserDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                post("/api/users/user")
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(newUser))
                        )
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                UserDto.class
        );

        assertEquals(8L, repository.count());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenUpdateUserByUserHimself_thenReturnOkStatus() throws Exception {

        UserDto userForUpdate = new UserDto("Alex", "alex2000@gmail.com", RoleType.ROLE_USER);

        mockMvc
                .perform(
                        put("/api/users/user")
                                .with(httpBasic("Alex", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userForUpdate))
                )
                .andExpect(status().isAccepted());
    }

    @Test
    public void whenUpdateUserByOtherUser_thenReturnForbiddenStatus() throws Exception {

        UserDto userForUpdate = new UserDto("Alex", "alex2000@gmail.com", RoleType.ROLE_USER);

        mockMvc
                .perform(
                        put("/api/users/user")
                                .with(httpBasic("Nick", "nick"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userForUpdate))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteUserByHimself_thenReturnOkStatus() throws Exception {

        String userNameForDelete = "Nick";

        mockMvc
                .perform(
                        delete("/api/users/user/" + userNameForDelete)
                                .with(httpBasic("Nick", "nick"))
                )
                .andExpect(status().isOk());

        assertEquals(6L, repository.count());
    }

    @Test
    public void whenDeleteUserByOtherUser_thenReturnForbiddenStatus() throws Exception {

        String userNameForDelete = "Bill";

        mockMvc
                .perform(
                        delete("/api/users/user/" + userNameForDelete)
                                .with(httpBasic("Nick", "nick"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteUserByAdmin_thenReturnOkStatus() throws Exception {

        String userNameForDelete = "Nick";

        mockMvc
                .perform(
                        delete("/api/users/user/" + userNameForDelete)
                                .with(httpBasic("John", "john"))
                )
                .andExpect(status().isOk());

        assertEquals(6L, repository.count());
    }
}
