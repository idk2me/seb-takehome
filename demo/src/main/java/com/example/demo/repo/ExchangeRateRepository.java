package com.example.demo.repo;

import com.example.demo.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

  List<ExchangeRate> findByDateOrderByCurrency_Code(LocalDate date);

  List<ExchangeRate> findByCurrency_CodeAndDateBetweenOrderByDate(
      String code, LocalDate from, LocalDate to);

  ExchangeRate findTopByCurrency_CodeOrderByDateDesc(String code);

  @Query("SELECT MAX(e.date) FROM ExchangeRate e")
  LocalDate findMaxDate();
}
