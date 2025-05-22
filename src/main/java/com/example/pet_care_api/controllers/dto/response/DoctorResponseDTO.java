package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoctorResponseDTO {

    private Long id;
    private String doctorName;
    private String qualifications;
    private int phoneNumber;

    private Long petClinicId;
}
