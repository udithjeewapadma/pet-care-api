package com.example.pet_care_api.controllers.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
