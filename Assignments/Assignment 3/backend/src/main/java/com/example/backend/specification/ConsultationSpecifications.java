package com.example.backend.specification;

import com.example.backend.model.Consultation;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ConsultationSpecifications {

    public static Specification<Consultation> withDoctorAndTimeslot(Integer id, Date date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("doctor").get("id"), id),
                criteriaBuilder.equal(root.get("scheduled"), date)
        );
    }

    public static Specification<Consultation> withPatientAndTimeslot(Integer id, Date date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("patient").get("id"), id),
                criteriaBuilder.equal(root.get("scheduled"), date)
        );
    }
}
