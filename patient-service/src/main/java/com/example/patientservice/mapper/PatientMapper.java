package com.example.patientservice.mapper;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PatientMapper {
    public PatientResponseDTO toDTO(Patient patient) {
        return new PatientResponseDTO(patient.getId().toString(), patient.getName(), patient.getEmail(), patient.getAddress(), patient.getDateOfBirth().toString());
    }

    public Patient toPatient(PatientRequestDTO dto) {
        return new Patient(dto.getName(), dto.getEmail(), dto.getAddress(), LocalDate.parse(dto.getDateOfBirth()), LocalDate.parse(dto.getRegisteredDate()));
    }
}
