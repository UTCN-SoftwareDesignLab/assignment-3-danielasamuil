package com.example.backend.service;

import com.example.backend.mapper.ConsultationMapper;
import com.example.backend.model.Consultation;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.repository.ConsultationRepository;
import com.example.backend.repository.PatientRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.backend.model.ConsultationSpecifications.equalDates;
import static com.example.backend.model.ConsultationSpecifications.freeDoctor;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final UserRepository userRepository;

    private final PatientRepository patientRepository;

    private final ConsultationRepository consultationRepository;

    private final ConsultationMapper consultationMapper;

    private Consultation findById(Integer id) {
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

    public ConsultationDto update(Integer id, ConsultationDto consultationDto){
        User doctor;

        Consultation consultation = findById(id);

        if(userRepository.findById(consultationDto.getDoctorId()) != null) {
            doctor = userRepository.findById(consultationDto.getDoctorId()).get();
        }
        else doctor = null;

        List<Consultation> notFreeDoctor =  consultationRepository.findAll(
                freeDoctor(doctor.getId()).and(equalDates(consultationDto.getScheduled())));

        consultation.setDetails(consultationDto.getDetails());

        if(notFreeDoctor.size() == 0){
            consultation.setScheduled(consultationDto.getScheduled());
        }

        return consultationMapper.toDto(
                consultationRepository.save(consultation)
        );
    }

    public boolean create(ConsultationDto consultationDto) {
        User doctor;

        if(patientRepository.findById(consultationDto.getPatientId()) != null ) {
            Patient patient = patientRepository.findById(consultationDto.getPatientId()).get();
        } else
            return false;

        if(userRepository.findById(consultationDto.getDoctorId()) != null) {
            doctor = userRepository.findById(consultationDto.getDoctorId()).get();
        } else
            return false;

        List<Consultation> notFreeDoctor =  consultationRepository.findAll(
                freeDoctor(doctor.getId()).and(equalDates(consultationDto.getScheduled())));

        if(notFreeDoctor.size() == 0){
            consultationRepository.save(consultationMapper.fromDto(consultationDto));
            return true;
        }
        else
            return false;
    }
}
