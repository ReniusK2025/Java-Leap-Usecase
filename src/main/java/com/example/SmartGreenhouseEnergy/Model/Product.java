package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type; // SERVICE or GOODS
    private String unit;
    private Double unitPrice;
}
