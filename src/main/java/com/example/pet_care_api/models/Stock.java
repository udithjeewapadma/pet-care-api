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
    private String name;
    private String description;
    private String itemCode;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @ManyToOne
    @JoinColumn(name = "stockCategory_id")
    private StockCategory stockCategory;

    @ManyToOne
    @JoinColumn(name = "petClinic_id")
    private PetClinic petClinic;
}
