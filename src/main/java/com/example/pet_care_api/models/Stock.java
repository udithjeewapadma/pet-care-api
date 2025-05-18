package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

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
}
