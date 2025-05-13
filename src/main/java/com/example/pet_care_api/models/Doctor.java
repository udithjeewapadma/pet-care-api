package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String qualifications;
    private int phoneNumber;

    @ManyToOne
    private PetShop petShop;

    @OneToMany(mappedBy = "doctor")
    private List<Pet> pets;
}
