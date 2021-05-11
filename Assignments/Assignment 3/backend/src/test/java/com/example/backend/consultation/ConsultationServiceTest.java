package com.example.backend.consultation;

import com.example.backend.TestCreationFactory;
import com.example.backend.mapper.ConsultationMapper;
import com.example.backend.mapper.PatientMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Consultation;
import com.example.backend.model.Patient;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.model.dtos.UserDto;
import com.example.backend.repository.ConsultationRepository;
import com.example.backend.repository.PatientRepository;
import com.example.backend.repository.RoleRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.backend.model.ERole.SECRETARY;
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
    private PatientService patientService;

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
        User doctor = (User) TestCreationFactory.listOf(User.class).get(0);

        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);

        ConsultationDto consultationDTO = ConsultationDto.builder()
                .id(consultation.getId())
                .details(consultation.getDetails())
                .doctorId(doctor.getId())
                .patientId(patient.getId())
                .scheduled(consultation.getScheduled())
                .build();

        when(consultationMapper.fromDto(consultationDTO)).thenReturn(consultation);
        when(consultationMapper.toDto(consultation)).thenReturn(consultationDTO);
        when(userService.findById(consultationDTO.getDoctorId())).thenReturn(doctor);
        when(patientService.findById(consultationDTO.getPatientId())).thenReturn(patient);
        when(consultationRepository.save(consultation)).thenReturn(consultation);

        Assertions.assertEquals(consultationService.create(consultationDTO), consultationDTO);
    }

    @Test
    void editConsultation() throws Exception {
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        User doctor = (User) TestCreationFactory.listOf(User.class).get(0);

        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);

        ConsultationDto consultationDto = ConsultationDto.builder()
                .id(consultation.getId())
                .scheduled(consultation.getScheduled())
                .patientId(patient.getId())
                .doctorId(doctor.getId())
                .details(consultation.getDetails())
                .build();

        when(consultationMapper.fromDto(consultationDto)).thenReturn(consultation);
        when(consultationMapper.toDto(consultation)).thenReturn(consultationDto);
        when(userService.findById(consultationDto.getDoctorId())).thenReturn(doctor);
        when(patientService.findById(consultationDto.getPatientId())).thenReturn(patient);
        when(consultationRepository.save(consultation)).thenReturn(consultation);
        when(consultationRepository.findById(consultation.getId())).thenReturn(Optional.of(consultation));

        Assertions.assertEquals(consultationService.update(consultationDto.getId(), consultationDto), consultationDto);
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
        User doctor = (User) TestCreationFactory.listOf(User.class).get(0);

        Consultation consultation = (Consultation) TestCreationFactory.listOf(Consultation.class).get(0);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);

        when(consultationRepository.save(consultation)).thenReturn(consultation);

        consultationRepository.delete(consultation);

        List<ConsultationDto> all = consultationService.findAll();
        Assertions.assertEquals(all.size(), 0);
    }
}

