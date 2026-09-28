package com.example.SmartGreenhouseEnergy.Service;

import com.example.SmartGreenhouseEnergy.Model.ThermalStorageTank;
import com.example.SmartGreenhouseEnergy.Model.Telemetry;
import com.example.SmartGreenhouseEnergy.Repository.ThermalStorageTankRepository;
import com.example.SmartGreenhouseEnergy.Repository.TelemetryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThermalStorageService {
    private final ThermalStorageTankRepository tankRepository;
    private final TelemetryRepository telemetryRepository;

    public ThermalStorageService(ThermalStorageTankRepository tankRepository,
                                 TelemetryRepository telemetryRepository) {
        this.tankRepository = tankRepository;
        this.telemetryRepository = telemetryRepository;
    }

    public ThermalStorageTank saveTank(ThermalStorageTank tank) {
        if (tank.getEfficiencyPercent() == null) tank.setEfficiencyPercent(90.0);
        if (tank.getActive() == null) tank.setActive(true);
        return tankRepository.save(tank);
    }

    public List<ThermalStorageTank> allTanks() {
        return tankRepository.findAll();
    }

    public ThermalStorageTank getTank(Long id) {
        return tankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tank not found: " + id));
    }

    public ThermalStorageTank updateTank(Long id, ThermalStorageTank value) {
        ThermalStorageTank tank = getTank(id);
        tank.setTankCode(value.getTankCode());
        tank.setZone(value.getZone());
        tank.setCapacityLiters(value.getCapacityLiters());
        tank.setCurrentTemperatureC(value.getCurrentTemperatureC());
        tank.setTargetTemperatureC(value.getTargetTemperatureC());
        tank.setEfficiencyPercent(value.getEfficiencyPercent());
        tank.setActive(value.getActive());
        return tankRepository.save(tank);
    }

    public void deleteTank(Long id) {
        tankRepository.delete(getTank(id));
    }

    public Telemetry processTelemetry(Telemetry telemetry) {
        ThermalStorageTank tank = getTank(telemetry.getTankId());

        if (telemetry.getRecordedAt() == null) {
            telemetry.setRecordedAt(LocalDateTime.now());
        }
        if (telemetry.getZone() == null || telemetry.getZone().isBlank()) {
            telemetry.setZone(tank.getZone());
        }

        double delta = Math.abs(
                telemetry.getOutletTemperatureC() - telemetry.getInletTemperatureC()
        );
        double liters = telemetry.getWaterVolumeLiters();

        // Water specific heat = 4.186 kJ/kg°C and 1 litre water ≈ 1 kg.
        double grossKwh = (liters * 4.186 * delta) / 3600.0;
        double efficiency =
                (tank.getEfficiencyPercent() == null
                        ? 100.0
                        : tank.getEfficiencyPercent()) / 100.0;
        double netKwh = grossKwh * efficiency;

        telemetry.setDeltaTemperatureC(round(delta));
        telemetry.setGrossEnergyKwh(round(grossKwh));
        telemetry.setNetHeatDeliveredKwh(round(netKwh));

        tank.setCurrentTemperatureC(telemetry.getOutletTemperatureC());
        tankRepository.save(tank);

        return telemetryRepository.save(telemetry);
    }

    public List<Telemetry> allTelemetry() {
        return telemetryRepository.findAll();
    }

    public void deleteTelemetry(Long id) {
        telemetryRepository.deleteById(id);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
