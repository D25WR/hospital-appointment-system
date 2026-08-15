package com.divya.hospital.dto;

import com.divya.hospital.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class AuthDtos {

    @Data
    public static class RegisterRequest {
        @NotBlank private String fullName;
        @NotBlank @Email private String email;
        @NotBlank private String password;
        private String phone;
        @NotNull private Role role; // PATIENT or DOCTOR
    }

    @Data
    public static class LoginRequest {
        @NotBlank @Email private String email;
        @NotBlank private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String fullName;
        private String role;

        public AuthResponse(String token, String fullName, String role) {
            this.token = token; this.fullName = fullName; this.role = role;
        }
    }
}
