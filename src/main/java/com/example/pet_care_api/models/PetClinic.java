package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "petClinics")
public class PetClinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clinicName;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "petClinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Doctor> doctors;

    @ManyToMany(mappedBy = "petClinics",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dealer> dealers;

    @OneToMany(mappedBy = "petClinic",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stock> stocks;
}
