package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dealers")
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;

    @ManyToMany(mappedBy = "dealers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pharmacy> pharmacies;
}
