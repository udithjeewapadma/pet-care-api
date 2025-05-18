package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String breedName;

    @ManyToOne
    @JoinColumn(name = "petCategory_id")
    private PetCategory petCategory;
}
