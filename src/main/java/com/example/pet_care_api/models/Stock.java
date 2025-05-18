package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VisibilityStatus visibilityStatus;

    @OneToOne
    @JoinColumn(name = "pet_clinic_id")
    private PetClinic petClinic;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<FoodStock> foodStocks;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MedicineStock> medicineStocks;
}
