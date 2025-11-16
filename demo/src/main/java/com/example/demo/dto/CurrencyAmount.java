package com.example.demo.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

public class CurrencyAmount {

  @JacksonXmlProperty(localName = "Ccy")
  private String currency;

  @JacksonXmlProperty(localName = "Amt")
  private BigDecimal amount;

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
