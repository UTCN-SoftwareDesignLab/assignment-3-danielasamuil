package com.example.backend.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PatientArrivedMessage {
    private String dateOfConsultation;
    private Integer patientId;
}
