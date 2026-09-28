package com.example.SmartGreenhouseEnergy.Config;

import com.example.SmartGreenhouseEnergy.Model.*;
import com.example.SmartGreenhouseEnergy.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(ContactRepository contacts, ProductRepository products,
                           ThermalStorageTankRepository tanks, ChartOfAccountRepository coa) {
        return args -> {
            if (contacts.count() == 0) {
                contacts.save(new Contact(null, "Green Valley Growers", "GROWER",
                        "accounts@greenvalley.example", "9876543210", "Thermal Storage Zone 3"));
                contacts.save(new Contact(null, "SunFlow Pumps Pvt Ltd", "VENDOR",
                        "sales@sunflow.example", "9876500000", "Industrial Estate"));
            }
            if (products.count() == 0) {
                products.save(new Product(null, "Greenhouse Thermal Heating kWh", "SERVICE", "kWh", 8.50));
                products.save(new Product(null, "Solar Thermal Collector Repair", "SERVICE", "JOB", 4500.00));
                products.save(new Product(null, "Water Pump Assembly", "GOODS", "UNIT", 18000.00));
            }
            if (tanks.count() == 0) {
                tanks.save(new ThermalStorageTank(null, "TANK-Z3-001", "Thermal Storage Zone 3",
                        10000.0, 45.0, 60.0, 90.0, true));
            }
            if (coa.count() == 0) {
                coa.save(new ChartOfAccount(null, "1100", "Thermal Storage Tanks", "ASSET", true));
                coa.save(new ChartOfAccount(null, "1110", "Solar Heat Collectors", "ASSET", true));
                coa.save(new ChartOfAccount(null, "1120", "Cash/Bank", "ASSET", true));
                coa.save(new ChartOfAccount(null, "2100", "Pump Supplier Payables", "LIABILITY", true));
                coa.save(new ChartOfAccount(null, "2110", "Maintenance Creditors", "LIABILITY", true));
                coa.save(new ChartOfAccount(null, "4100", "Greenhouse Heating Revenue", "INCOME", true));
                coa.save(new ChartOfAccount(null, "5100", "Circulation Pump Power Expenses", "EXPENSE", true));
                coa.save(new ChartOfAccount(null, "5110", "Solar Collector Servicing Costs", "EXPENSE", true));
            }
        };
    }
}
