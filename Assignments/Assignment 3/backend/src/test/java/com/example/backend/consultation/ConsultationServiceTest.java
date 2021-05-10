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
import com.example.backend.service.PatientService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ConsultationServiceTest {
    @InjectMocks
    private ConsultationService consultationService;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consultationService = new ConsultationService(userService, patientService, consultationRepository, consultationMapper);
    }

    @Test
    void findAll() {
        List<Consultation> consultations = TestCreationFactory.listOf(Consultation.class);
        when(consultationRepository.findAll()).thenReturn(consultations);

        List<ConsultationDto> all = consultationService.findAll();
        Assertions.assertEquals(all.size(), consultations.size());
    }

    @Test
    void createConsultation() throws Exception {

        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        User user = (User) TestCreationFactory.listOf(User.class).get(0);

        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(user);
        consultation.setPatient(patient);

        ConsultationDto consultationDTO = ConsultationDto.builder()
                .id(consultation.getId())
                .details(consultation.getDetails())
                .build();

        when(consultationMapper.fromDto(consultationDTO)).thenReturn(consultation);
        when(consultationMapper.toDto(consultation)).thenReturn(consultationDTO);
        when(consultationRepository.save(consultation)).thenReturn(consultation);

        when(userRepository.findById(consultationDTO.getDoctorId())).thenReturn(Optional.of(user));
        when(patientRepository.findById(consultationDTO.getPatientId())).thenReturn(Optional.of(patient));

        Assertions.assertEquals(consultationService.create(consultationDTO), consultation);
    }

    @Test
    void editConsultation() throws Exception {
        Consultation consultation1 = Consultation.builder()
                .details("Details1")
                .build();

        ConsultationDto consultation2 = ConsultationDto.builder()
                .details("Details2")
                .build();

        when(consultationRepository.findById(consultation1.getId())).thenReturn(Optional.of(consultation1));

        consultationService.update(consultation1.getId(), consultation2);
        Assertions.assertEquals("Details2", consultation1.getDetails());
    }

    @Test
    void deleteAll() {
        List<Consultation> consultations = TestCreationFactory.listOf(Consultation.class);
        consultationService.deleteAll();

        List<ConsultationDto> all = consultationService.findAll();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void delete() {
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        User user = (User) TestCreationFactory.listOf(User.class).get(0);

        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(user);
        consultation.setPatient(patient);

        when(consultationRepository.save(consultation)).thenReturn(consultation);

        consultationRepository.delete(consultation);

        List<ConsultationDto> all = consultationService.findAll();
        Assertions.assertEquals(all.size(), 0);
    }
}
