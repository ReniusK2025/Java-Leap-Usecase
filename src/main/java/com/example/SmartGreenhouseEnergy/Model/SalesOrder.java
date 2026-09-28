package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long growerId;
    private Long productId;
    private Double energyKwh;
    private Double unitPrice;
    private Double totalAmount;
    private String zone;
    private String status;
    private LocalDate orderDate;
}
