package com.example.pet_care_api.controllers.dto.request;

import com.example.pet_care_api.models.AvailabilityStatus;
import lombok.Data;

@Data
public class CreateStockRequestDTO {

    private String name;
    private String description;
    private String itemCode;
    private AvailabilityStatus availabilityStatus;
}
