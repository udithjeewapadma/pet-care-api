package com.example.pet_care_api.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "foodStocks")
public class FoodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemCode;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
