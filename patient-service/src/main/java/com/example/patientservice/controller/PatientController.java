package com.example.patientservice.controller;

import com.example.patientservice.dto.PatientResponseDTO;
import com.example.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        return ResponseEntity.ok(service.getPatients());
    }
}
