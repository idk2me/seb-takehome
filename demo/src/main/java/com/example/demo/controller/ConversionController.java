package com.example.demo.controller;

import com.example.demo.dto.ConversionRequest;
import com.example.demo.service.ConversionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/convert")
public class ConversionController {

  private final ConversionService service;

  public ConversionController(ConversionService service) {
    this.service = service;
  }

  @PostMapping
  public Object convert(@RequestBody ConversionRequest req) {
    var result = service.convert(req.getFrom(), req.getTo(), req.getAmount());
    return result;
  }
}
