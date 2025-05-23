package com.example.patientservice.service;

import com.example.patientservice.dto.PatientRequestDTO;
import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.exception.EmailAlreadyExistsException;
import com.example.patientservice.exception.PatientNotFoundException;
import com.example.patientservice.grpc.BillingServiceGrpcClient;
import com.example.patientservice.mapper.PatientMapper;
import com.example.patientservice.model.Patient;
import com.example.patientservice.repo.PatientRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepo repo;
    private final PatientMapper mapper;
    private final BillingServiceGrpcClient grpcClient;

    public PatientService(PatientRepo repo, PatientMapper mapper, BillingServiceGrpcClient grpcClient) {
        this.repo = repo;
        this.mapper = mapper;
        this.grpcClient = grpcClient;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = repo.findAll();
        return patients.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto) {
        Patient patient = repo.findById(id).orElseThrow(() -> new PatientNotFoundException("patient not found with ID: " + id));
        if (repo.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A Patient with this email already exists " + dto.getEmail());
        }
        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));

        return mapper.toDTO(repo.save(patient));
    }

    @Transactional
    public UUID createPatient(PatientRequestDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("A Patient with this email already exists " + dto.getEmail());
        }
        Patient patient = repo.save(mapper.toPatient(dto));
        grpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());
        return patient.getId();
    }

    public void deletePatient(UUID id) {
        repo.deleteById(id);
    }
}
