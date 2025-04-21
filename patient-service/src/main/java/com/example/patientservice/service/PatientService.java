package com.example.patientservice.service;

import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repo.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepo repo;
    private final PatientMapper mapper;

    public PatientService(PatientRepo repo, PatientMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = repo.findAll();
        return patients.stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
