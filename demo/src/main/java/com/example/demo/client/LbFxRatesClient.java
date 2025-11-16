package com.example.demo.client;

import com.example.demo.dto.FxRateResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LbFxRatesClient {

  private final RestClient rest = RestClient.create();
  private final ObjectMapper xmlMapper = (ObjectMapper) XmlMapper.builder()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .addModule(new JavaTimeModule())
      .build();

  private static final String URL = "https://www.lb.lt/webservices/fxrates/FxRates.asmx/getCurrentFxRates?tp=EU";

  public FxRateResponse getCurrentRates() {
    String xml = rest.get()
        .uri(URL)
        .retrieve()
        .body(String.class);

    System.out.println("XML length: " + xml.length());
    System.out.println("XML preview: " + xml.substring(0, Math.min(200, xml.length())));

    try {
      return xmlMapper.readValue(xml, FxRateResponse.class);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
