package com.example.backend.consultation;

import com.example.backend.BaseControllerTest;
import com.example.backend.controller.ConsultationController;
import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.backend.TestCreationFactory.listOf;
import static com.example.backend.TestCreationFactory.randomInt;
import static com.example.backend.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultationControllerTest extends BaseControllerTest {

    @InjectMocks
    private ConsultationController consultationController;

    @Mock
    private ConsultationService consultationService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        consultationController = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();
    }

    @Test
    void allPatients() throws Exception {
        List<ConsultationDto> consultationDtoList = listOf(ConsultationDto.class);
        when(consultationService.findAll()).thenReturn(consultationDtoList);

        ResultActions result = mockMvc.perform(get(CONSULTATIONS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDtoList));

    }

    @Test
    void create() throws Exception {
        ConsultationDto consultationDto = (ConsultationDto) listOf(ConsultationDto.class).get(0);
        when(consultationService.create(consultationDto)).thenReturn(consultationDto);

        ResultActions result = performPostWithRequestBody(CONSULTATIONS, consultationDto);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDto));
    }

    @Test
    void edit() throws Exception {
        ConsultationDto consultationDto = (ConsultationDto) listOf(ConsultationDto.class).get(0);
        when(consultationService.update(consultationDto.getId(), consultationDto)).thenReturn(consultationDto);

        ResultActions result = performPutWithRequestBodyAndPathVariable(CONSULTATIONS + ENTITY, consultationDto, consultationDto.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDto));
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(consultationService).deleteAll();

        ResultActions result = performDelete(CONSULTATIONS);
        result.andExpect(status().isOk());
        verify(consultationService, times(1)).deleteAll();
    }

    @Test
    void delete() throws Exception {
        int id = randomInt();
        doNothing().when(consultationService).delete(id);

        ResultActions result = performDeleteWithPathVariable(CONSULTATIONS + ENTITY, id);
        result.andExpect(status().isOk());
        verify(consultationService, times(1)).delete(id);
    }
}

