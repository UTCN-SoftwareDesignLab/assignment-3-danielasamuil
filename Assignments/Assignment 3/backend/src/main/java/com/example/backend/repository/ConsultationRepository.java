package com.example.backend.repository;

import com.example.backend.model.Consultation;
import com.example.backend.model.dtos.ConsultationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer>, JpaSpecificationExecutor<Consultation> {
}
