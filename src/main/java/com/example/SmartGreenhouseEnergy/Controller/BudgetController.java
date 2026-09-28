package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.Budget;
import com.example.SmartGreenhouseEnergy.Service.BudgetService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService service;
    public BudgetController(BudgetService service) { this.service=service; }
    @PostMapping public Budget create(@RequestBody Budget x) { return service.save(x); }
    @GetMapping public List<Budget> all() { return service.all(); }
    @GetMapping("/report/{zone}") public BudgetService.BudgetReport report(@PathVariable String zone) { return service.report(zone); }
}
