package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartOfAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountCode;
    private String accountName;
    private String accountType; // ASSET, LIABILITY, INCOME, EXPENSE
    private Boolean active;
}
