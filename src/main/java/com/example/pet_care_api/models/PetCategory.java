package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "petCategories")
public class PetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categoryName;

    @OneToMany(mappedBy = "petCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pets;
}
