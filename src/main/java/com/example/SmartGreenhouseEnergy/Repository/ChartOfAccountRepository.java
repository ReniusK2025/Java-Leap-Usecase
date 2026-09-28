package com.example.SmartGreenhouseEnergy.Repository;

import com.example.SmartGreenhouseEnergy.Model.ChartOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartOfAccountRepository extends JpaRepository<ChartOfAccount, Long> {
}
