package com.example.SmartGreenhouseEnergy.Service;

import com.example.SmartGreenhouseEnergy.Model.Budget;
import com.example.SmartGreenhouseEnergy.Repository.BudgetRepository;
import com.example.SmartGreenhouseEnergy.Repository.JournalEntryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository repository;
    private final JournalEntryRepository journals;

    public BudgetService(BudgetRepository repository, JournalEntryRepository journals) {
        this.repository = repository;
        this.journals = journals;
    }

    public Budget save(Budget b) {
        return repository.save(b);
    }

    public List<Budget> all() {
        return repository.findAll();
    }

    public Budget findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found: " + id));
    }

    public Budget update(Long id, Budget value) {
        Budget budget = findById(id);
        budget.setZone(value.getZone());
        budget.setSeason(value.getSeason());
        budget.setHeatingRevenueBudget(value.getHeatingRevenueBudget());
        budget.setPumpPowerExpenseBudget(value.getPumpPowerExpenseBudget());
        budget.setServicingExpenseBudget(value.getServicingExpenseBudget());
        return repository.save(budget);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public BudgetReport report(String zone) {
        List<Budget> budgets = repository.findByZone(zone);

        double revenueBudget = budgets.stream()
                .mapToDouble(x -> n(x.getHeatingRevenueBudget())).sum();
        double pumpBudget = budgets.stream()
                .mapToDouble(x -> n(x.getPumpPowerExpenseBudget())).sum();
        double serviceBudget = budgets.stream()
                .mapToDouble(x -> n(x.getServicingExpenseBudget())).sum();

        double revenueActual = journals.findAll().stream()
                .filter(j -> "Greenhouse Heating Revenue".equals(j.getAccountName())
                        && "CREDIT".equals(j.getDebitCredit()))
                .mapToDouble(j -> n(j.getAmount())).sum();

        double pumpActual = journals.findAll().stream()
                .filter(j -> "Circulation Pump Power Expenses".equals(j.getAccountName())
                        && "DEBIT".equals(j.getDebitCredit()))
                .mapToDouble(j -> n(j.getAmount())).sum();

        double serviceActual = journals.findAll().stream()
                .filter(j -> "Solar Collector Servicing Costs".equals(j.getAccountName())
                        && "DEBIT".equals(j.getDebitCredit()))
                .mapToDouble(j -> n(j.getAmount())).sum();

        return new BudgetReport(
                zone, revenueBudget, revenueActual,
                pumpBudget, pumpActual,
                serviceBudget, serviceActual
        );
    }

    private double n(Double x) {
        return x == null ? 0 : x;
    }

    public record BudgetReport(
            String zone,
            double revenueBudget,
            double revenueActual,
            double pumpPowerBudget,
            double pumpPowerActual,
            double servicingBudget,
            double servicingActual
    ) {}
}
