package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "pet-shops")
public class PetShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopName;
    private String shopAddress;
    private String shopPhoneNumber;

    @OneToMany(mappedBy = "petShop")
    private List<Doctor> doctors;
}
