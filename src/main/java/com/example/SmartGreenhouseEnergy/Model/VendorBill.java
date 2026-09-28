package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long purchaseOrderId;
    private Long vendorId;
    private Double amount;
    private String status; // OPEN, PAID
    private LocalDate billDate;
}
