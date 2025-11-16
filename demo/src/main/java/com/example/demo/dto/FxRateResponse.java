package com.example.demo.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

@JacksonXmlRootElement(localName = "FxRates")
public class FxRateResponse {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "FxRate")
  private List<FxRate> fxRate;

  public List<FxRate> getFxRate() {
    return fxRate;
  }

  public void setFxRate(List<FxRate> fxRate) {
    this.fxRate = fxRate;
  }
}
