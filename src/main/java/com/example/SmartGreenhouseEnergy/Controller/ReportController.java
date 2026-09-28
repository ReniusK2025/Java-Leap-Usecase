package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Service.ReportService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService service;
    public ReportController(ReportService service) { this.service=service; }
    @GetMapping("/dashboard") public Map<String,Object> dashboard() { return service.dashboard(); }
    @GetMapping("/profit-loss") public Map<String,Object> profitLoss() { return service.profitLoss(); }
    @GetMapping("/balance-sheet") public Map<String,Object> balanceSheet() { return service.balanceSheet(); }
}
