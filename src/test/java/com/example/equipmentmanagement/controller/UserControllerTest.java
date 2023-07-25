package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.dto.UserDto;
import com.example.equipmentmanagement.exception.UserNotFoundException;
import com.example.equipmentmanagement.model.User;
import com.example.equipmentmanagement.service.UserService;
import com.example.equipmentmanagement.system.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    String baseUrl = "/api/v1";

    List<User> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        User u1 = new User();
        u1.setUserId(1);
        u1.setUserName("User1");
        u1.setEmail("user1@hotmail.com");
        u1.setUserPassword("user1Password");
        u1.setStatus("active");
        userList.add(u1);

        User u2 = new User();
        u2.setUserId(2);
        u2.setUserName("User2");
        u2.setEmail("user2@hotmail.com");
        u2.setUserPassword("user2Password");
        u2.setStatus("active");
        userList.add(u2);


        User u3 = new User();
        u3.setUserId(3);
        u3.setUserName("User3");
        u3.setEmail("user3@hotmail.com");
        u3.setUserPassword("user3Password");
        u3.setStatus("active");
        userList.add(u3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindUserByIdSuccess() throws Exception {

        given(userService.findUserById(1)).willReturn(userList.get(0));

        mockMvc.perform(get(baseUrl+"/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success."))
                .andExpect(jsonPath("$.data.userId").value(1));

    }

    @Test
    void testFindUserByIdNotFound() throws Exception {

        given(userService.findUserById(1)).willThrow(new UserNotFoundException(1));

        mockMvc.perform(get(baseUrl+"/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not found user with id: 1"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testFindAllUsersSuccess() throws Exception{
        given(userService.findAllUser()).willReturn(userList);

        mockMvc.perform(get(baseUrl+"/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success."))
                .andExpect(jsonPath("$.data[0].userId").value(1))
                .andExpect(jsonPath("$.data[1].userId").value(2));
    }

    @Test
    void testCreateUserSuccess() throws Exception {

        UserDto userDto = new UserDto(4,"mockUser4","mockuser4@hotmail.com","Active");

        String json = objectMapper.writeValueAsString(userDto);

        User newUser = new  User();
        newUser.setUserId(4);
        newUser.setUserName("mockUser4");
        newUser.setUserPassword("mock_password4");
        newUser.setEmail("mockuser4@hotmail.com");
        newUser.setStatus("Active");

        given(userService.createUser(Mockito.any(User.class))).willReturn(newUser);

        mockMvc.perform(post(baseUrl+"/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add One Success."))
                .andExpect(jsonPath("$.data.userId").value(4))
                .andExpect(jsonPath("$.data.userName").value("mockUser4"));
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        UserDto userDto = new UserDto(1,"second","testemail",null);
        String json = objectMapper.writeValueAsString(userDto);

        User updatedUser = new User();
        updatedUser.setUserId(1);
        updatedUser.setUserName("second");
        updatedUser.setEmail("updatedEmail");
        updatedUser.setStatus("Active");
        updatedUser.setUserPassword("updatedPassword");

        given(userService.updateUser(eq(1),Mockito.any(User.class))).willReturn(updatedUser);

        mockMvc.perform(put(baseUrl+"/users/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update One Success."))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.userName").value("second"))
                .andExpect(jsonPath("$.data.email").value("updatedEmail"))
                .andExpect(jsonPath("$.data.status").value("Active"));
    }

    @Test
    void testUpdateUserErrorWithNonExistedId () throws Exception {
        UserDto userDto = new UserDto(4,"mockUser4","mockuser4@hotmail.com","Active");

        String json = objectMapper.writeValueAsString(userDto);

        given(userService.updateUser(eq(1),Mockito.any(User.class))).willThrow(new UserNotFoundException(1));

        this.mockMvc.perform(put(baseUrl+"/users/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not found user with id: 1"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        doNothing().when(userService).deleteUser(4);

        this.mockMvc.perform(delete(baseUrl+"/users/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete One Success."))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testDeleteUserErrorWithNonExistedUserId() throws Exception {
        doThrow(new UserNotFoundException(4)).when(userService).deleteUser(4);

        this.mockMvc.perform(delete(baseUrl+"/users/4").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not found user with id: 4"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    }





