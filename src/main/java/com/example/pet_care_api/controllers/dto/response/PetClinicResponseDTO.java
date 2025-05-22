package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetClinicResponseDTO {

    private Long id;
    private String clinicName;
    private String address;
    private String phoneNumber;
}
