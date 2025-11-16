import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CurrencyService, ExchangeRate, LatestRatesResponse } from '../currency.service';

@Component({
  selector: 'app-rates',
  imports: [CommonModule, FormsModule],
  templateUrl: './rates.html',
  styleUrl: './rates.css',
})
export class Rates implements OnInit {
  private currencyService = inject(CurrencyService);
  private router = inject(Router);
  
  protected rates = signal<ExchangeRate[]>([]);
  protected date = signal<string | null>(null);
  protected loading = signal<boolean>(true);
  protected error = signal<string | null>(null);
  protected searchTerm = signal<string>('');

  ngOnInit() {
    this.loadRates();
  }

  loadRates() {
    this.loading.set(true);
    this.error.set(null);

    this.currencyService.getLatestRates().subscribe({
      next: (response: LatestRatesResponse) => {
        this.rates.set(response.rates);
        this.date.set(response.date);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading rates:', err);
        this.error.set('Failed to load exchange rates. Please try again.');
        this.loading.set(false);
      }
    });
  }

  viewHistory(currency: string) {
    this.router.navigate(['/history', currency]);
  }

  filteredRates() {
    const term = this.searchTerm().toLowerCase();
    if (!term) return this.rates();
    return this.rates().filter(r => r.currency.toLowerCase().includes(term));
  }
}
