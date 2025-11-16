package com.example.demo.dto;

import java.math.BigDecimal;

public class ConversionRequest {
  private String from;
  private String to;
  private BigDecimal amount;

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
