package com.example.pet_care_api.controllers.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateDealerRequestDTO {

    private String dealerName;
    private String phoneNumber;
    private String email;
    private List<Long> petClinicIds;
}
