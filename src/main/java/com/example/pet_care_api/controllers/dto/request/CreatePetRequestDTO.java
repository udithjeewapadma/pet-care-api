package com.example.pet_care_api.controllers.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePetRequestDTO {
    private String petName;
    private String gender;
    private String age;
    private String weight;
    private String birthDate;

    private List<MultipartFile> imageFiles;

}
