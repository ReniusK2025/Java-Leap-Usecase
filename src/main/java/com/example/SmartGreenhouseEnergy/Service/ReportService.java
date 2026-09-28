package com.example.SmartGreenhouseEnergy.Service;

import com.example.SmartGreenhouseEnergy.Repository.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReportService {
    private final JournalEntryRepository journals;
    private final TelemetryRepository telemetry;
    private final ThermalStorageTankRepository tanks;
    private final VendorBillRepository vendorBills;

    public ReportService(JournalEntryRepository journals, TelemetryRepository telemetry,
                         ThermalStorageTankRepository tanks, VendorBillRepository vendorBills) {
        this.journals=journals; this.telemetry=telemetry; this.tanks=tanks; this.vendorBills=vendorBills;
    }

    public Map<String,Object> dashboard() {
        double kwh = telemetry.findAll().stream().mapToDouble(x -> n(x.getNetHeatDeliveredKwh())).sum();
        return Map.of(
                "tankCount", tanks.count(),
                "telemetryCount", telemetry.count(),
                "totalHeatDeliveredKwh", round(kwh),
                "openVendorBills", vendorBills.findAll().stream().filter(x -> "OPEN".equals(x.getStatus())).count()
        );
    }

    public Map<String,Object> profitLoss() {
        double revenue = sum("Greenhouse Heating Revenue", "CREDIT");
        double pump = sum("Circulation Pump Power Expenses", "DEBIT");
        double servicing = sum("Solar Collector Servicing Costs", "DEBIT");
        return Map.of("heatingRevenue", revenue, "pumpPowerExpenses", pump, "servicingExpenses", servicing,
                "netProfit", round(revenue - pump - servicing));
    }

    public Map<String,Object> balanceSheet() {
        double cash = sum("Cash/Bank", "DEBIT") - sum("Cash/Bank", "CREDIT");
        double payables = sum("Maintenance Creditors", "CREDIT") - sum("Maintenance Creditors", "DEBIT");
        double receivables = sum("Customer Receivables", "DEBIT") - sum("Customer Receivables", "CREDIT");
        return Map.of("thermalStorageTanks", tanks.count(), "cashBankBalance", round(cash),
                "equipmentVendorPayables", round(payables), "customerReceivables", round(receivables));
    }

    private double sum(String account, String dc) {
        return round(journals.findAll().stream()
                .filter(j -> account.equals(j.getAccountName()) && dc.equals(j.getDebitCredit()))
                .mapToDouble(x -> n(x.getAmount())).sum());
    }
    private double n(Double x) { return x == null ? 0 : x; }
    private double round(double x) { return Math.round(x*100.0)/100.0; }
}
