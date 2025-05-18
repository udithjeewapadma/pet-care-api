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
    private String dealerName;
    private String phoneNumber;
    private String email;

    @ManyToMany(mappedBy = "dealers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pharmacy> pharmacies;

    @OneToMany(mappedBy = "dealer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
