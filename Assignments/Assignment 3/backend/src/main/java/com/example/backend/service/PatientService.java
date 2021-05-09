package com.example.backend.service;

import com.example.backend.mapper.PatientMapper;
import com.example.backend.model.Patient;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public Patient findById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    public List<PatientDto> findAll() {

        return patientRepository.findAll().stream().
                map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteAll() {

        patientRepository.deleteAll();
    }

    public void delete(int id) {

        patientRepository.deleteById(id);
    }

    public PatientDto update(Integer id, PatientDto patientDto) {

        Patient patient = findById(id);

        patient.setName(patientDto.getName());
        patient.setAddress(patientDto.getAddress());
        patient.setBirthDate(patientDto.getBirthDate());
        patient.setPNC(patientDto.getPNC());
        patient.setIdentityCardNr(patientDto.getIdentityCardNr());

        return patientMapper.toDto(
                patientRepository.save(patient)
        );
    }

    public PatientDto create(PatientDto patientDto) {
        return patientMapper.toDto(patientRepository.save(
                patientMapper.fromDto(patientDto)));
    }
}
