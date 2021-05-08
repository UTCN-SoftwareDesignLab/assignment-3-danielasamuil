package com.example.backend.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private Integer id;
    private String name;
    private Integer identityCardNr;
    private String PNC;
    private Date birthDate;
    private String address;
}
