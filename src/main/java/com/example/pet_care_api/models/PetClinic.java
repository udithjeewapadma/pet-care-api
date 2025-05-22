package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "pet-shops")
public class PetClinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clinicName;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "petClinic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Doctor> doctors;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "petShop_dealer",
            joinColumns = @JoinColumn(name = "petClinicId"),
            inverseJoinColumns = @JoinColumn(name = "dealerId")
    )
    private List<Dealer> dealers;
}
