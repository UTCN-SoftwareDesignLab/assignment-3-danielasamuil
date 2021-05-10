package com.example.backend.controller;

import com.example.backend.model.dtos.PatientDto;
import com.example.backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.backend.UrlMapping.ENTITY;
import static com.example.backend.UrlMapping.PATIENTS;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping()
    public List<PatientDto> findAll() {
        return patientService.findAll();
    }

    @PostMapping()
    public PatientDto create(@RequestBody PatientDto patient) {
        return patientService.create(patient);
    }

    @PutMapping(ENTITY)
    public PatientDto edit(@PathVariable Integer id, @RequestBody PatientDto patient) {
        return patientService.update(id, patient);
    }

    @DeleteMapping()
    public void deleteAll() {
        patientService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Integer id) {
        patientService.delete(id);
    }
}
