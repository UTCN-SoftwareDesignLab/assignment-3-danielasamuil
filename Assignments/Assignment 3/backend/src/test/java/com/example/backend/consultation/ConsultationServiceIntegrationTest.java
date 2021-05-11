package com.example.backend.consultation;

import com.example.backend.TestCreationFactory;
import com.example.backend.mapper.ConsultationMapper;
import com.example.backend.model.Consultation;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.repository.ConsultationRepository;
import com.example.backend.repository.PatientRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ConsultationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConsultationServiceIntegrationTest {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @BeforeEach
    void setUp() {
        consultationRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Consultation> consultations = TestCreationFactory.listOf(Consultation.class);

        for (Consultation consultation : consultations) {
            consultation.setPatient(patientRepository.save(consultation.getPatient()));
            consultation.setDoctor(userRepository.save(consultation.getDoctor()));
        }

        consultationRepository.saveAll(consultations);

        List<ConsultationDto> consultationDto = consultationService.findAll();

        int i = 0;

        for (Consultation consultation : consultations) {
            assertEquals(consultation.getDetails(), consultationDto.get(i).getDetails());
            i++;
        }
    }

    @Test
    void createAll() throws Exception {

    }

    @Test
    void updateAll() throws Exception {

    }

    @Test
    void deleteAll() {
        consultationRepository.deleteAll();

        List<ConsultationDto> all = consultationService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}
