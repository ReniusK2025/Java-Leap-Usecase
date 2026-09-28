package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long salesOrderId;
    private Long growerId;
    private Double energyKwh;
    private Double amount;
    private String status; // OPEN, PAID
    private LocalDate invoiceDate;
}
