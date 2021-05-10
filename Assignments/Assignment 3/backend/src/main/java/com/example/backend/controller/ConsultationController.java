package com.example.backend.controller;

import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.backend.UrlMapping.CONSULTATIONS;
import static com.example.backend.UrlMapping.ENTITY;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping()
    public List<ConsultationDto> findAll() {
        return consultationService.findAll();
    }

    @PostMapping()
    public ConsultationDto create(@RequestBody ConsultationDto consultation) throws Exception {
        return consultationService.create(consultation);
    }

    @PutMapping(ENTITY)
    public ConsultationDto edit(@PathVariable Integer id, @RequestBody ConsultationDto consultation) throws Exception {
        return consultationService.update(id, consultation);
    }

    @DeleteMapping()
    public void deleteAll() {
        consultationService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Integer id) {
        consultationService.delete(id);
    }
}
