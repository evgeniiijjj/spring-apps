package com.example.newsservice.controllers;

import com.example.newsservice.AbstractTest;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForCreate;
import com.example.newsservice.dtos.UserDtoForDelete;
import com.example.newsservice.dtos.UserRoleDto;
import com.example.newsservice.entities.enums.RoleType;
import com.example.newsservice.repositories.UserRepository;
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

public class UserControllerTest extends AbstractTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void whenGetUsersByUserWithRoleAdmin_thenReturnUserList() throws Exception {

        List<UserDto> actualResult = objectMapper.readValue(
            mockMvc
                    .perform(
                            get("/api/users?pageNumber=0&pageSize=10")
                                    .with(httpBasic("john@gmail.com", "john"))
                    )
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                new TypeReference<>() {}
        );

        assertEquals(7, actualResult.size());
    }

    @Test
    public void whenGetUsersByUserWithRoleNoAdmin_thenReturnForbiddenStatus() throws Exception {

        mockMvc
                .perform(
                        get("/api/users?pageNumber=0&pageSize=10")
                                .with(httpBasic("alex@gmail.com", "alex"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenCreateUser_thenReturnOkStatus() throws Exception {

        UserDtoForCreate newUser = new UserDtoForCreate("Bob", "Marley", "bob@gmail.com", "bob");

        UserDto actualResult = objectMapper.readValue(
                mockMvc
                        .perform(
                                post("/api/users/user")
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(objectMapper.writeValueAsString(newUser))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                UserDto.class
        );

        assertEquals(8L, repository.count());

        assertEquals(newUser.getUserDto(), actualResult);
    }

    @Test
    public void whenAddUserRoleByUserWithRoleAdmin_thenReturnOkStatus() throws Exception {

        UserRoleDto userRole = new UserRoleDto("bill@gmail.com", RoleType.ROLE_MODERATOR);

        mockMvc
                .perform(
                        put("/api/users/user/role")
                                .with(httpBasic("john@gmail.com", "john"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userRole))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddUserRoleByUserWithRoleNoAdmin_thenReturnForbiddenStatus() throws Exception {

        UserRoleDto userRole = new UserRoleDto("bill@gmail.com", RoleType.ROLE_MODERATOR);

        mockMvc
                .perform(
                        put("/api/users/user/role")
                                .with(httpBasic("alex@gmail.com", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userRole))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenUpdateUserByUserHimself_thenReturnOkStatus() throws Exception {

        UserDto user = new UserDto("Alex", "Romanov", "alex@gmail.com");

        mockMvc
                .perform(
                        put("/api/users/user")
                                .with(httpBasic("alex@gmail.com", "alex"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateUserByOtherUser_thenReturnForbiddenStatus() throws Exception {

        UserDto user = new UserDto("Alex", "Romanov", "alex@gmail.com");

        mockMvc
                .perform(
                        put("/api/users/user")
                                .with(httpBasic("nick@gmail.com", "nick"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenDeleteUserByHimself_thenReturnOkStatus() throws Exception {

        UserDtoForDelete user = new UserDtoForDelete("nick@gmail.com");

        mockMvc
                .perform(
                        delete("/api/users/user")
                                .with(httpBasic("nick@gmail.com", "nick"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isOk());

        assertEquals(6L, repository.count());
    }

    @Test
    public void whenDeleteUserByOtherUser_thenReturnForbiddenStatus() throws Exception {

        UserDtoForDelete user = new UserDtoForDelete("alex@gmail.com");

        mockMvc
                .perform(
                        delete("/api/users/user")
                                .with(httpBasic("nick@gmail.com", "nick"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isForbidden());
    }
}
