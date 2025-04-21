package com.example.patientservice.mapper;

import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public PatientResponseDTO toDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId().toString(),
                patient.getName(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getDateOfBirth().toString()
        );
    }
}
