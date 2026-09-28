package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendorId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalAmount;
    private String status;
    private LocalDate orderDate;
}
