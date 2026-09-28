package com.example.SmartGreenhouseEnergy.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type; // GROWER or VENDOR
    private String email;
    private String phone;
    private String address;
}
