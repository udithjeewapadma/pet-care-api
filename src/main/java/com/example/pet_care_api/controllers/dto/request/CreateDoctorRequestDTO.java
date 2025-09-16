package com.example.pet_care_api.controllers.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateDoctorRequestDTO {

    @NotBlank(message = "Doctor name is required")
    @Size(max = 50, message = "Doctor name must not exceed 50 characters")
    private String doctorName;

    @NotBlank(message = "Qualifications are required")
    @Size(max = 100, message = "Qualifications must not exceed 100 characters")
    private String qualifications;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
}
