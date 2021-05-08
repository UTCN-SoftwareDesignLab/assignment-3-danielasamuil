package com.example.backend.mapper;

import com.example.backend.model.Patient;
import com.example.backend.model.dtos.PatientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);

    Patient fromDto(PatientDto patientDto);
}
