package com.divya.hospital.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

public class AppointmentDtos {

    @Data
    public static class BookingRequest {
        @NotNull private Long doctorId;
        @NotNull private LocalDateTime scheduledAt;
        private String reasonForVisit;
    }

    @Data
    @AllArgsConstructor
    public static class AppointmentResponse {
        private Long id;
        private String patientName;
        private String doctorName;
        private String specialization;
        private LocalDateTime scheduledAt;
        private String status;
        private String reasonForVisit;
    }
}
