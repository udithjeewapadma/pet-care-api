package com.example.pet_care_api.controllers.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockCategoryResponseDTO {

    private Long id;
    private String categoryName;
}
