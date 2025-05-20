package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BreedResponseDTO {

    private Long id;
    private String breedName;
    private Long petCategoryId;
}
