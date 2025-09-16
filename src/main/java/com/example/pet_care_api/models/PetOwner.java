package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "petOwners")
public class PetOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerName;
    private String address;
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;


    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "petOwner",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pet> pets;
}
