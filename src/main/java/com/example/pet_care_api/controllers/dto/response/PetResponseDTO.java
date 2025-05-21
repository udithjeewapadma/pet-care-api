package com.example.pet_care_api.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDTO {
    private Long id;
    private String petName;
    private String gender;
    private String birthDate;
    private List<String> imageUrls;
    private Long petOwnerId;
    private Long doctorId;
    private Long petCategoryId;

}
