package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "pharmacies")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pharmacyName;
    private String address;
    private String phoneNumber;
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "pharmacy_dealer",
            joinColumns = @JoinColumn(name = "pharmacyId"),
            inverseJoinColumns = @JoinColumn(name = "dealerId")
    )
    private List<Dealer> dealers;
}
