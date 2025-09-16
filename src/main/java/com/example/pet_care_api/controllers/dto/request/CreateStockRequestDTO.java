package com.example.pet_care_api.controllers.dto.request;

import com.example.pet_care_api.models.AvailabilityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStockRequestDTO {

    @NotBlank(message = "Stock name is required")
    @Size(max = 100, message = "Stock name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Item code is required")
    @Size(max = 50, message = "Item code must not exceed 50 characters")
    private String itemCode;

    @NotNull(message = "Availability status is required")
    private AvailabilityStatus availabilityStatus;
}
