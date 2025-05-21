package com.example.pet_care_api.controllers.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePetRequestDTO {
    private String petName;
    private String gender;
    private String birthDate;

    private List<MultipartFile> imageFiles;

}
