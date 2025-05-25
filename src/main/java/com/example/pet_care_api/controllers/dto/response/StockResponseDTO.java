package com.example.pet_care_api.controllers.dto.response;

import com.example.pet_care_api.models.AvailabilityStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String itemCode;
    private AvailabilityStatus availabilityStatus;
    private Long petClinicId;
    private Long StockCategoryId;
}
