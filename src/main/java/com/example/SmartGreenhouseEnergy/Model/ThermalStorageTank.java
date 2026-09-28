package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThermalStorageTank {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tankCode;
    private String zone;
    private Double capacityLiters;
    private Double currentTemperatureC;
    private Double targetTemperatureC;
    private Double efficiencyPercent;
    private Boolean active;
}
