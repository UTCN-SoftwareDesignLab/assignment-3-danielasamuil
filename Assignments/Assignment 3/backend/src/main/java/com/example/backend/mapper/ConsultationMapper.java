package com.example.backend.mapper;

import com.example.backend.model.Consultation;
import com.example.backend.model.dtos.ConsultationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    ConsultationDto toDto(Consultation consultation);

    Consultation fromDto(ConsultationDto consultationDto);
}
