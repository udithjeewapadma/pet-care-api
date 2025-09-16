package com.example.pet_care_api.controllers.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreatePetCategoryRequestDTO {

    @NotBlank(message = "Pet Category name is required")
    @Size(max = 50, message = "Pet Category name must not exceed 50 characters")
    private String categoryName;
}
