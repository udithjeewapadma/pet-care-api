package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "petOwners")
public class PetOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerName;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "petOwner",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pets;
}
