package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telemetry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tankId;
    private String zone;
    private LocalDateTime recordedAt;
    private Double inletTemperatureC;
    private Double outletTemperatureC;
    private Double waterVolumeLiters;
    private Double deltaTemperatureC;
    private Double grossEnergyKwh;
    private Double netHeatDeliveredKwh;
}
