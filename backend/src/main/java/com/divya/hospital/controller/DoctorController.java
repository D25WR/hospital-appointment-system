package com.divya.hospital.controller;

import com.divya.hospital.entity.Doctor;
import com.divya.hospital.entity.DoctorAvailability;
import com.divya.hospital.repository.DoctorAvailabilityRepository;
import com.divya.hospital.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository availabilityRepository;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAll(@RequestParam(required = false) String specialization) {
        if (specialization != null) {
            return ResponseEntity.ok(doctorRepository.findBySpecializationIgnoreCase(specialization));
        }
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @PostMapping("/availability")
    public ResponseEntity<DoctorAvailability> addAvailability(@RequestBody DoctorAvailability availability) {
        return ResponseEntity.ok(availabilityRepository.save(availability));
    }
}
