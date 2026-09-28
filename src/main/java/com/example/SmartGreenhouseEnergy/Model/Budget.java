package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zone;
    private String season;
    private Double heatingRevenueBudget;
    private Double pumpPowerExpenseBudget;
    private Double servicingExpenseBudget;
}
