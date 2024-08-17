package com.example.gstbilling.repository;

import com.example.gstbilling.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySaleDate(LocalDate saleDate);
    List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
    // Other methods
}
