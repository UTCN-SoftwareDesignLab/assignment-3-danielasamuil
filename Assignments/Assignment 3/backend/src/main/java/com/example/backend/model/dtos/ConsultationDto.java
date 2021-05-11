package com.example.backend.model.dtos;

import com.example.backend.model.Patient;
import com.example.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDto {

    private Integer id;
    private Date scheduled;
    private String details;
    private Integer patientId;
    private Integer doctorId;
}
