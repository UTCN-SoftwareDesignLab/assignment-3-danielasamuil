package com.example.backend.model;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ConsultationSpecifications {

    public static Specification<Consultation> freeDoctor(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("doctor").get("id"), id);
    }

    public static Specification<Consultation> equalDates(Date date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("scheduled"), date);
    }

}
