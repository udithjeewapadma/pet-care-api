package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petName;
    private String gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String birthDate;

    @ElementCollection
    private List<String> imageUrl;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "petOwner_id")
    private PetOwner petOwner;

    @ManyToOne
    @JoinColumn(name = "petCategory_id")
    private PetCategory petCategory;
}
