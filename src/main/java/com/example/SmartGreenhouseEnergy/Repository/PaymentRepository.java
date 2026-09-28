package com.example.SmartGreenhouseEnergy.Repository;

import com.example.SmartGreenhouseEnergy.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
