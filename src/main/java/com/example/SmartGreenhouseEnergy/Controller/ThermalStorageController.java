package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.*;
import com.example.SmartGreenhouseEnergy.Service.ThermalStorageService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/thermal-storage")
public class ThermalStorageController {
    private final ThermalStorageService service;
    public ThermalStorageController(ThermalStorageService service) { this.service=service; }

    @PostMapping("/tanks") public ThermalStorageTank createTank(@RequestBody ThermalStorageTank x) { return service.saveTank(x); }
    @GetMapping("/tanks") public List<ThermalStorageTank> tanks() { return service.allTanks(); }
    @GetMapping("/tanks/{id}") public ThermalStorageTank tank(@PathVariable Long id) { return service.getTank(id); }

    @PostMapping("/telemetry") public Telemetry telemetry(@RequestBody Telemetry x) { return service.processTelemetry(x); }
    @GetMapping("/telemetry") public List<Telemetry> telemetry() { return service.allTelemetry(); }
}
