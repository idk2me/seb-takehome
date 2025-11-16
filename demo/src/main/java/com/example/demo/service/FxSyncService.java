package com.example.demo.service;

import com.example.demo.client.LbFxRatesClient;
import com.example.demo.dto.FxRate;
import com.example.demo.dto.CurrencyAmount;
import com.example.demo.model.Currency;
import com.example.demo.model.ExchangeRate;
import com.example.demo.repo.CurrencyRepository;
import com.example.demo.repo.ExchangeRateRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class FxSyncService {

  private final LbFxRatesClient client;
  private final CurrencyRepository currencyRepo;
  private final ExchangeRateRepository rateRepo;

  public FxSyncService(LbFxRatesClient client,
      CurrencyRepository currencyRepo,
      ExchangeRateRepository rateRepo) {
    this.client = client;
    this.currencyRepo = currencyRepo;
    this.rateRepo = rateRepo;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void initializeRatesOnStartup() {
    syncCurrentRates();
  }

  @Transactional
  public void syncCurrentRates() {
    var response = client.getCurrentRates();
    if (response == null || response.getFxRate() == null) {
      System.out.println("NO DATA RETURNED FROM LB API");
      return;
    }

    System.out.println("Fetched " + response.getFxRate().size() + " rates");

    for (FxRate fx : response.getFxRate()) {
      if (fx.getAmounts() == null || fx.getAmounts().size() < 2)
        continue;

      var list = fx.getAmounts();

      var eur = list.stream()
          .filter(a -> "EUR".equals(a.getCurrency()))
          .findFirst()
          .orElse(null);

      var foreign = list.stream()
          .filter(a -> !"EUR".equals(a.getCurrency()))
          .findFirst()
          .orElse(null);

      if (eur == null || foreign == null)
        continue;

      var date = fx.getDate();
      var c = currencyRepo.findById(foreign.getCurrency())
          .orElseGet(() -> currencyRepo.save(new Currency(foreign.getCurrency())));

      var rateValue = foreign.getAmount().divide(eur.getAmount(), 8, RoundingMode.HALF_UP);
      rateRepo.save(new ExchangeRate(c, date, rateValue));
    }
  }
}
