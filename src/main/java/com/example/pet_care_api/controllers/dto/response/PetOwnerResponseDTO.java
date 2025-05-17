package com.example.pet_care_api.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetOwnerResponseDTO {

    private Long id;
    private String userName;
    private String address;
    private String phoneNumber;
}
