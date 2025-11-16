package com.example.demo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FxScheduler {

  private final FxSyncService sync;

  public FxScheduler(FxSyncService sync) {
    this.sync = sync;
  }

  // Run every day at 03:00 Vilnius time
  @Scheduled(cron = "0 0 3 * * *", zone = "Europe/Vilnius")
  public void dailyUpdate() {
    System.out.println("Running daily FX sync...");
    sync.syncCurrentRates();
  }
}
