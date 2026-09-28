package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentType; // CUSTOMER or VENDOR
    private Long referenceId;
    private Double amount;
    private String method; // BANK_TRANSFER
    private LocalDate paymentDate;
}
