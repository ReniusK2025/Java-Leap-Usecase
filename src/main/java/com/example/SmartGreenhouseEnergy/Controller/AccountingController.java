package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.ChartOfAccount;
import com.example.SmartGreenhouseEnergy.Model.JournalEntry;
import com.example.SmartGreenhouseEnergy.Repository.ChartOfAccountRepository;
import com.example.SmartGreenhouseEnergy.Repository.JournalEntryRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounting")
public class AccountingController {
    private final ChartOfAccountRepository coaRepository;
    private final JournalEntryRepository journalRepository;

    public AccountingController(ChartOfAccountRepository coaRepository, JournalEntryRepository journalRepository) {
        this.coaRepository = coaRepository;
        this.journalRepository = journalRepository;
    }

    @PostMapping("/coa")
    public ChartOfAccount createCoA(@RequestBody ChartOfAccount account) {
        if (account.getActive() == null) account.setActive(true);
        return coaRepository.save(account);
    }

    @GetMapping("/coa")
    public List<ChartOfAccount> coa() {
        return coaRepository.findAll();
    }

    @PostMapping("/pump-power-expense")
    public List<JournalEntry> recordPumpPowerExpense(@RequestBody PumpPowerExpenseRequest request) {
        JournalEntry debit = journalRepository.save(new JournalEntry(
                null, LocalDate.now(), "BANK", "Circulation Pump Power Expenses", "DEBIT",
                request.amount(), "PUMP_POWER_EXPENSE", request.referenceId(), request.description()));

        JournalEntry credit = journalRepository.save(new JournalEntry(
                null, LocalDate.now(), "BANK", "Cash/Bank", "CREDIT",
                request.amount(), "PUMP_POWER_EXPENSE", request.referenceId(), request.description()));

        return List.of(debit, credit);
    }

    public record PumpPowerExpenseRequest(Double amount, Long referenceId, String description) {}
}
