package com.divya.hospital.service;

import com.divya.hospital.dto.AppointmentDtos.*;
import com.divya.hospital.entity.*;
import com.divya.hospital.exception.ResourceNotFoundException;
import com.divya.hospital.exception.SlotUnavailableException;
import com.divya.hospital.repository.AppointmentRepository;
import com.divya.hospital.repository.DoctorAvailabilityRepository;
import com.divya.hospital.repository.DoctorRepository;
import com.divya.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Core booking logic: solves the real-world problem of double-booking by checking
 * a doctor's weekly availability window AND any overlapping existing appointment
 * (based on that doctor's consultation duration) before confirming a new booking.
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public AppointmentResponse book(String patientEmail, BookingRequest request) {
        User patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        validateWithinAvailability(doctor, request.getScheduledAt());
        validateNoOverlap(doctor, request.getScheduledAt());

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .scheduledAt(request.getScheduledAt())
                .reasonForVisit(request.getReasonForVisit())
                .status(AppointmentStatus.PENDING)
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return toResponse(saved);
    }

    private void validateWithinAvailability(Doctor doctor, LocalDateTime scheduledAt) {
        List<DoctorAvailability> slots = availabilityRepository
                .findByDoctorIdAndDayOfWeek(doctor.getId(), scheduledAt.getDayOfWeek());
        boolean withinWindow = slots.stream().anyMatch(slot ->
                !scheduledAt.toLocalTime().isBefore(slot.getStartTime()) &&
                !scheduledAt.toLocalTime().isAfter(slot.getEndTime()));
        if (!slots.isEmpty() && !withinWindow) {
            throw new SlotUnavailableException("Doctor is not available at the requested time");
        }
    }

    private void validateNoOverlap(Doctor doctor, LocalDateTime scheduledAt) {
        int duration = doctor.getConsultationDurationMinutes() != null ? doctor.getConsultationDurationMinutes() : 30;
        LocalDateTime windowStart = scheduledAt.minusMinutes(duration - 1);
        LocalDateTime windowEnd = scheduledAt.plusMinutes(duration - 1);

        boolean conflict = appointmentRepository
                .findByDoctorIdAndScheduledAtBetweenAndStatusNot(doctor.getId(), windowStart, windowEnd, AppointmentStatus.CANCELLED)
                .stream().anyMatch(a -> true);

        if (conflict) {
            throw new SlotUnavailableException("This time slot overlaps with an existing appointment");
        }
    }

    public List<AppointmentResponse> getForPatient(String patientEmail) {
        User patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return appointmentRepository.findByPatientIdOrderByScheduledAtDesc(patient.getId())
                .stream().map(this::toResponse).toList();
    }

    public List<AppointmentResponse> getForDoctor(String doctorEmail) {
        Doctor doctor = doctorRepository.findByUserEmail(doctorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return appointmentRepository.findByDoctorIdOrderByScheduledAtDesc(doctor.getId())
                .stream().map(this::toResponse).toList();
    }

    public AppointmentResponse updateStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointment.setStatus(status);
        return toResponse(appointmentRepository.save(appointment));
    }

    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getPatient().getFullName(),
                a.getDoctor().getUser().getFullName(),
                a.getDoctor().getSpecialization(),
                a.getScheduledAt(),
                a.getStatus().name(),
                a.getReasonForVisit()
        );
    }
}
