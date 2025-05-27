package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DealerResponseDTO {
    private Long id;
    private String dealerName;
    private String phoneNumber;
    private String email;
    private List<PetClinicDTO> petClinics;
}
