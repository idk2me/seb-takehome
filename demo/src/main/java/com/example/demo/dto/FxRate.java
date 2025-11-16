package com.example.demo.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.time.LocalDate;
import java.util.List;

public class FxRate {

  @JacksonXmlProperty(localName = "Tp")
  private String type;

  @JacksonXmlProperty(localName = "Dt")
  private LocalDate date;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "CcyAmt")
  private List<CurrencyAmount> amounts;

  public String getType() {
    return type;
  }

  public LocalDate getDate() {
    return date;
  }

  public List<CurrencyAmount> getAmounts() {
    return amounts;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setAmounts(List<CurrencyAmount> amounts) {
    this.amounts = amounts;
  }
}
