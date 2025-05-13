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
    private String petBreed;
    private String petGender;
    private String petAge;
    private String petWeight;
    private String birthDate;

    @ManyToOne
    private Doctor doctor;
}
