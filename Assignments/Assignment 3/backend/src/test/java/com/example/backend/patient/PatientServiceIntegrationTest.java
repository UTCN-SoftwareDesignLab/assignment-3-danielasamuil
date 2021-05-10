package com.example.backend.patient;

import com.example.backend.model.Patient;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.repository.PatientRepository;
import com.example.backend.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PatientServiceIntegrationTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .name("Name " + i)
                    .address("Address" + i)
                    .birthDate(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
                    .identityCardNr(i)
                    .PNC("PNC " + i)
                    .build();
            patients.add(patient);
            patientRepository.save(patient);
        }

        List<PatientDto> patientDtos = patientService.findAll();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDtos.get(i).getId());
            assertEquals(patients.get(i).getName(), patientDtos.get(i).getName());
        }
    }

    @Test
    void createAll() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .name("Name " + i)
                    .address("Address" + i)
                    .birthDate(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
                    .identityCardNr(i)
                    .PNC("PNC " + i)
                    .build();
            patients.add(patient);
            patientRepository.save(patient);
        }

        List<PatientDto> patientDtos = patientService.findAll();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDtos.get(i).getId());
            assertEquals(patients.get(i).getName(), patientDtos.get(i).getName());
        }
    }

    @Test
    void updateAll() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .name("Name " + i)
                    .address("Address" + i)
                    .birthDate(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
                    .identityCardNr(i)
                    .PNC("PNC " + i)
                    .build();
            patients.add(patient);

            patient.setAddress("Some other address" + i);
            patientRepository.save(patient);
        }

        List<PatientDto> patientDtos = patientService.findAll();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDtos.get(i).getId());
            assertEquals(patients.get(i).getName(), patientDtos.get(i).getName());
        }
    }

    @Test
    void deleteAll() {
        patientRepository.deleteAll();

        List<PatientDto> all = patientService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}
