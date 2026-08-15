package com.divya.hospital.controller;

import com.divya.hospital.dto.AppointmentDtos.*;
import com.divya.hospital.entity.AppointmentStatus;
import com.divya.hospital.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> book(Authentication auth, @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(appointmentService.book(auth.getName(), request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponse>> myAppointments(Authentication auth) {
        return ResponseEntity.ok(appointmentService.getForPatient(auth.getName()));
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<AppointmentResponse>> doctorAppointments(Authentication auth) {
        return ResponseEntity.ok(appointmentService.getForDoctor(auth.getName()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id, @RequestParam AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }
}
