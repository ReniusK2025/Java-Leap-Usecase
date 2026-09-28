package com.example.SmartGreenhouseEnergy.Repository;

import com.example.SmartGreenhouseEnergy.Model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByZone(String zone);
}
