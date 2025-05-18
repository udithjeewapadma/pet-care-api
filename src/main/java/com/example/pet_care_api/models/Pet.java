package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petName;
    private String gender;
    private String age;
    private String weight;
    private String birthDate;

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
