package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 35)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer identityCardNr;

    @Column(nullable = false, unique = true)
    private String PNC;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, length = 35)
    private String address;

}
