package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate entryDate;
    private String journalType; // SALES, PURCHASE, CASH, BANK
    private String accountName;
    private String debitCredit; // DEBIT or CREDIT
    private Double amount;
    private String referenceType;
    private Long referenceId;
    private String description;
}
