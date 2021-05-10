package com.example.backend.patient;

import com.example.backend.BaseControllerTest;
import com.example.backend.controller.PatientController;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.service.PatientService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PatientControllerTest extends BaseControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        patientController = new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void allPatients() throws Exception {
        List<PatientDto> patientDTOList = listOf(PatientDto.class);
        when(patientService.findAll()).thenReturn(patientDTOList);

        ResultActions result = mockMvc.perform(get(PATIENTS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTOList));

    }

    @Test
    void create() throws Exception {
        PatientDto patient = (PatientDto) listOf(PatientDto.class).get(0);
        when(patientService.create(patient)).thenReturn(patient);

        ResultActions result = performPostWithRequestBody(PATIENTS, patient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }

    @Test
    void edit() throws Exception {
        PatientDto patient = (PatientDto) listOf(PatientDto.class).get(0);
        when(patientService.update(patient.getId(), patient)).thenReturn(patient);

        ResultActions result = performPutWithRequestBodyAndPathVariable(PATIENTS + ENTITY, patient, patient.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(patientService).deleteAll();

        ResultActions result = performDelete(PATIENTS);
        result.andExpect(status().isOk());
        verify(patientService, times(1)).deleteAll();
    }

    @Test
    void delete() throws Exception {
        int id = randomInt();
        doNothing().when(patientService).delete(id);

        ResultActions result = performDeleteWithPathVariable(PATIENTS + ENTITY, id);
        result.andExpect(status().isOk());
        verify(patientService, times(1)).delete(id);
    }
}
