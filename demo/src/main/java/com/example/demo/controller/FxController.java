package com.example.demo.controller;

import com.example.demo.repo.CurrencyRepository;
import com.example.demo.repo.ExchangeRateRepository;
import com.example.demo.service.FxSyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FxController {

  private final FxSyncService syncService;
  private final CurrencyRepository currencyRepo;
  private final ExchangeRateRepository rateRepo;

  public FxController(FxSyncService syncService,
      CurrencyRepository currencyRepo,
      ExchangeRateRepository rateRepo) {
    this.syncService = syncService;
    this.currencyRepo = currencyRepo;
    this.rateRepo = rateRepo;
  }

  @GetMapping("/debug/currencies")
  public Object debugCurrencies() {
    return currencyRepo.findAll();
  }

  @GetMapping("/rates/latest")
  public Object getLatestRates() {
    syncService.syncCurrentRates();

    var date = rateRepo.findMaxDate();
    var rates = rateRepo.findByDateOrderByCurrency_Code(date);

    // Group by currency and take only the last entry for each
    var uniqueRates = rates.stream()
        .collect(java.util.stream.Collectors.toMap(
            r -> r.getCurrency().getCode(),
            r -> r,
            (existing, replacement) -> replacement // Keep the last one
        ))
        .values()
        .stream()
        .map(r -> Map.of(
            "currency", r.getCurrency().getCode(),
            "rate", r.getRate()))
        .sorted((a, b) -> ((String)a.get("currency")).compareTo((String)b.get("currency")))
        .toList();

    return Map.of(
        "date", date,
        "rates", uniqueRates);
  }

  @GetMapping("/rates/{code}/history")
  public List<Map<String, Object>> history(
      @PathVariable String code,
      @RequestParam LocalDate from,
      @RequestParam LocalDate to) {

    return rateRepo.findByCurrency_CodeAndDateBetweenOrderByDate(code, from, to)
        .stream()
        .map(r -> Map.<String, Object>of(
            "date", r.getDate(),
            "rate", r.getRate()))
        .toList();
  }
}
