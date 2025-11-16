package com.example.demo.service;

import com.example.demo.repo.ExchangeRateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class ConversionService {

  private final ExchangeRateRepository repo;

  public ConversionService(ExchangeRateRepository repo) {
    this.repo = repo;
  }

  public Result convert(String from, String to, BigDecimal amount) {
    LocalDate date = repo.findMaxDate();

    var fromRateEntity = from.equals("EUR") ? null : repo.findTopByCurrency_CodeOrderByDateDesc(from);
    if (!from.equals("EUR") && fromRateEntity == null) {
      throw new IllegalArgumentException("Missing rate for " + from);
    }
    BigDecimal rateFrom = from.equals("EUR")
        ? BigDecimal.ONE
        : fromRateEntity.getRate();

    var toRateEntity = to.equals("EUR") ? null : repo.findTopByCurrency_CodeOrderByDateDesc(to);
    if (!to.equals("EUR") && toRateEntity == null) {
      throw new IllegalArgumentException("Missing rate for " + to);
    }
    BigDecimal rateTo = to.equals("EUR")
        ? BigDecimal.ONE
        : toRateEntity.getRate();

    BigDecimal converted = amount;

    if (!from.equals("EUR")) {
      converted = converted.divide(rateFrom, 8, RoundingMode.HALF_UP);
    }

    if (!to.equals("EUR")) {
      converted = converted.multiply(rateTo);
    }

    converted = converted.setScale(4, RoundingMode.HALF_UP);

    BigDecimal usedCrossRate = rateTo.divide(rateFrom, 8, RoundingMode.HALF_UP);

    return new Result(converted, usedCrossRate, date);
  }

  public record Result(BigDecimal convertedAmount, BigDecimal rate, LocalDate date) {
  }
}
