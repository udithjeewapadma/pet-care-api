package com.example.pet_care_api.controllers.dto.request;

import lombok.Data;

@Data
public class RegisterPetOwnerRequest {
    private String ownerName;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
}
