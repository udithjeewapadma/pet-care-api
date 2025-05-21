package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PetResponseDTO {
    private Long id;
    private String petName;
    private String gender;
    private String birthDate;
    private List<String> imageUrls;

}
