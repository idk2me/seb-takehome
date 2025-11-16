import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CurrencyService, Currency, ConversionRequest } from '../currency.service';

@Component({
  selector: 'app-converter',
  imports: [CommonModule, FormsModule],
  templateUrl: './converter.html',
  styleUrl: './converter.css'
})
export class Converter implements OnInit {
  private currencyService = inject(CurrencyService);
  
  protected currencies = signal<Currency[]>([]);
  protected fromCurrency = signal<string>('USD');
  protected toCurrency = signal<string>('EUR');
  protected amount = signal<number>(100);
  protected convertedAmount = signal<number | null>(null);
  protected exchangeRate = signal<number | null>(null);
  protected conversionDate = signal<string | null>(null);
  protected loading = signal<boolean>(false);
  protected error = signal<string | null>(null);

  ngOnInit() {
    this.loadCurrencies();
  }

  private loadCurrencies() {
    this.currencyService.getCurrencies().subscribe({
      next: (currencies) => {
        this.currencies.set(currencies);
        if (currencies.length > 0) {
          const usd = currencies.find(c => c.code === 'USD');
          const eur = currencies.find(c => c.code === 'EUR');
          if (usd) this.fromCurrency.set('USD');
          if (eur) this.toCurrency.set('EUR');
        }
      },
      error: (err) => {
        console.error('Error loading currencies:', err);
        this.error.set('Failed to load currencies');
      }
    });
  }

  convert() {
    this.loading.set(true);
    this.error.set(null);
    this.convertedAmount.set(null);

    const request: ConversionRequest = {
      from: this.fromCurrency(),
      to: this.toCurrency(),
      amount: this.amount()
    };

    this.currencyService.convert(request).subscribe({
      next: (response) => {
        this.convertedAmount.set(response.convertedAmount);
        this.exchangeRate.set(response.rate);
        this.conversionDate.set(response.date);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error converting:', err);
        this.error.set('Failed to convert currency. Please try again.');
        this.loading.set(false);
      }
    });
  }

  swap() {
    const temp = this.fromCurrency();
    this.fromCurrency.set(this.toCurrency());
    this.toCurrency.set(temp);
  }
}
