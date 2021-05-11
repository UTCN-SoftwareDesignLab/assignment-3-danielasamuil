package com.example.backend.service;

import com.example.backend.mapper.ConsultationMapper;
import com.example.backend.model.Consultation;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.backend.specification.ConsultationSpecifications.*;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final UserService userService;

    private final PatientService patientService;

    private final ConsultationRepository consultationRepository;

    private final ConsultationMapper consultationMapper;

    public Consultation findById(Integer id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }

    public List<ConsultationDto> findAll() {

        return consultationRepository.findAll().stream().
                map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteAll() {

        consultationRepository.deleteAll();
    }

    public void delete(int id) {

        consultationRepository.deleteById(id);
    }

    private boolean dateIsAvailableForBothDoctorAndPatient(User doctor, Patient patient, ConsultationDto consultationDto) {

        List<Consultation> consultationsByDoctorAndTimeslot = consultationRepository.findAll(
                withDoctorAndTimeslot(doctor.getId(), consultationDto.getScheduled())
        );

        List<Consultation> consultationsByPatientAndTimeslot = consultationRepository.findAll(
                withPatientAndTimeslot(patient.getId(), consultationDto.getScheduled())
        );

        if (consultationsByDoctorAndTimeslot.isEmpty() && consultationsByPatientAndTimeslot.isEmpty())
            return true;
        else
            return false;
    }

    public ConsultationDto update(Integer id, ConsultationDto consultationDto) throws Exception {

        Consultation consultation = findById(id);

        User doctor = userService.findById(consultationDto.getDoctorId());

        Patient patient = patientService.findById(consultationDto.getPatientId());

        if (dateIsAvailableForBothDoctorAndPatient(doctor, patient, consultationDto)) {

            consultation.setDoctor(doctor);

            consultation.setPatient(patient);

            consultation.setDetails(consultationDto.getDetails());

            return consultationMapper.toDto(
                    consultationRepository.save(consultation)
            );

        } else {
            throw new Exception("Cannot update the consultation");
        }

    }

    public ConsultationDto create(ConsultationDto consultationDto) throws Exception {

        Patient patient = patientService.findById(consultationDto.getPatientId());

        User doctor = userService.findById(consultationDto.getDoctorId());

        if (dateIsAvailableForBothDoctorAndPatient(doctor, patient, consultationDto)) {
            return consultationMapper.toDto(
                    consultationRepository.save(consultationMapper.fromDto(consultationDto))
            );
        } else {
            throw new Exception("Cannot create the consultation");
        }
    }
}
