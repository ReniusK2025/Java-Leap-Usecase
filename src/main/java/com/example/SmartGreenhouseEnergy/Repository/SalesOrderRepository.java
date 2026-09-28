package com.example.SmartGreenhouseEnergy.Repository;

import com.example.SmartGreenhouseEnergy.Model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findByZone(String zone);
}
