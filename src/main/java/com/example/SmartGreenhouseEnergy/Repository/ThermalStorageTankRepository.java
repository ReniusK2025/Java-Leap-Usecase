package com.example.SmartGreenhouseEnergy.Repository;

import com.example.SmartGreenhouseEnergy.Model.ThermalStorageTank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThermalStorageTankRepository extends JpaRepository<ThermalStorageTank, Long> {
    List<ThermalStorageTank> findByZone(String zone);
}
