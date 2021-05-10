package com.example.backend.patient;

import com.example.backend.TestCreationFactory;
import com.example.backend.mapper.PatientMapper;
import com.example.backend.model.Patient;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.repository.PatientRepository;
import com.example.backend.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDto> all = patientService.findAll();
        Assertions.assertEquals(all.size(), patients.size());
    }

    @Test
    void createPatient() {

        Patient patient1 = Patient.builder()
                .name("Daniela")
                .address("Viitorului street nr 10")
                .build();

        PatientDto patient2 = PatientDto.builder()
                .name("Daniela")
                .address("Viitorului street nr 10")
                .build();

        when(patientMapper.fromDto(patient2)).thenReturn(patient1);
        when(patientMapper.toDto(patient1)).thenReturn(patient2);
        when(patientRepository.save(patient1)).thenReturn(patient1);

        Assertions.assertEquals(patient1.getId(), patientService.create(patient2).getId());
    }

    @Test
    void editPatient() {
        Patient patient1 = Patient.builder()
                .name("Daniela")
                .address("Viitorului street nr 10")
                .build();


        PatientDto patient2 = PatientDto.builder()
                .name("Bianca")
                .address("Rozelor street nr 20")
                .build();

        when(patientRepository.findById(patient1.getId())).thenReturn(Optional.of(patient1));

        patientService.update(patient1.getId(), patient2);
        Assertions.assertEquals("Bianca", patient2.getName());
    }

    @Test
    void deleteAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        patientRepository.deleteAll();

        List<PatientDto> all = patientService.findAll();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void delete() {
        Patient patient = Patient.builder()
                .name("Daniela")
                .address("Viitorului street nr 10")
                .build();

        when(patientRepository.save(patient)).thenReturn(patient);

        patientRepository.delete(patient);

        List<PatientDto> all = patientService.findAll();
        Assertions.assertEquals(all.size(), 0);
    }
}
