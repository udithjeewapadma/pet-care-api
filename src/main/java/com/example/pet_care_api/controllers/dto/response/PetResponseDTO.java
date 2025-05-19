package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

@Data
@NoArgsConstructor
public class PetResponseDTO {
    private Long id;
    private String petName;
    private String gender;
    private String age;
    private String weight;
    private String birthDate;
}
