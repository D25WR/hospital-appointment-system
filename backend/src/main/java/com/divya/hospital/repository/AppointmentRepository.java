package com.divya.hospital.repository;

import com.divya.hospital.entity.Appointment;
import com.divya.hospital.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientIdOrderByScheduledAtDesc(Long patientId);
    List<Appointment> findByDoctorIdOrderByScheduledAtDesc(Long doctorId);
    List<Appointment> findByDoctorIdAndScheduledAtBetweenAndStatusNot(
            Long doctorId, LocalDateTime start, LocalDateTime end, AppointmentStatus excluded);
}
