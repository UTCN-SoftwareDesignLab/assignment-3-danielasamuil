package com.example.backend.user;

import com.example.backend.BaseControllerTest;
import com.example.backend.TestCreationFactory;
import com.example.backend.controller.UserController;
import com.example.backend.model.dtos.UserDto;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.backend.TestCreationFactory.*;
import static com.example.backend.UrlMapping.ENTITY;
import static com.example.backend.UrlMapping.USERS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserDto> userDTOs = TestCreationFactory.listOf(UserDto.class);
        when(userService.findAll()).thenReturn(userDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTOs));
    }

    @Test
    void create() throws Exception {
        UserDto user = UserDto.builder()
                .id(randomInt())
                .email(randomEmail())
                .name(randomString())
                .password(randomString())
                .build();
        when(userService.create(user)).thenReturn(user);

        ResultActions result = performPostWithRequestBody(USERS,user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void edit() throws Exception {
        int id = randomInt();
        UserDto user = UserDto.builder()
                .name("something")
                .email("some_email@yahoo.com")
                .password("Password1")
                .build();

        when(userService.update(id, user)).thenReturn(user);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, user, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(userService).deleteAll();

        ResultActions result = performDelete(USERS);
        result.andExpect(status().isOk());
        verify(userService, times(1)).deleteAll();
    }

    @Test
    void delete() throws Exception {
        int id = randomInt();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk());
        verify(userService, times(1)).delete(id);
    }
}
