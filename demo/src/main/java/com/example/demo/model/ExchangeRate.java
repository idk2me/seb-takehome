package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class ExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "currency_code")
  private Currency currency;

  private LocalDate date;

  private BigDecimal rate;

  public ExchangeRate() {
  }

  public ExchangeRate(Currency currency, LocalDate date, BigDecimal rate) {
    this.currency = currency;
    this.date = date;
    this.rate = rate;
  }

  public Currency getCurrency() {
    return currency;
  }

  public LocalDate getDate() {
    return date;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }
}
