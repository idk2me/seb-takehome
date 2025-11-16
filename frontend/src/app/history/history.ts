import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CurrencyService, HistoryPoint } from '../currency.service';

@Component({
  selector: 'app-history',
  imports: [CommonModule, FormsModule],
  templateUrl: './history.html',
  styleUrl: './history.css',
})
export class History implements OnInit {
  private currencyService = inject(CurrencyService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  
  protected currencyCode = signal<string>('');
  protected history = signal<HistoryPoint[]>([]);
  protected loading = signal<boolean>(true);
  protected error = signal<string | null>(null);
  protected fromDate = signal<string>('');
  protected toDate = signal<string>('');

  ngOnInit() {
    const code = this.route.snapshot.paramMap.get('code');
    if (code) {
      this.currencyCode.set(code);
      this.initializeDates();
      this.loadHistory();
    }
  }

  private initializeDates() {
    const to = new Date();
    const from = new Date();
    from.setMonth(from.getMonth() - 3);
    
    this.toDate.set(to.toISOString().split('T')[0]);
    this.fromDate.set(from.toISOString().split('T')[0]);
  }

  loadHistory() {
    this.loading.set(true);
    this.error.set(null);

    this.currencyService.getHistory(
      this.currencyCode(),
      this.fromDate(),
      this.toDate()
    ).subscribe({
      next: (data) => {
        this.history.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading history:', err);
        this.error.set('Failed to load currency history. Please try again.');
        this.loading.set(false);
      }
    });
  }

  goBack() {
    this.router.navigate(['/rates']);
  }

  minRate() {
    const rates = this.history().map(h => h.rate);
    return rates.length ? Math.min(...rates) : 0;
  }

  maxRate() {
    const rates = this.history().map(h => h.rate);
    return rates.length ? Math.max(...rates) : 0;
  }

  avgRate() {
    const rates = this.history().map(h => h.rate);
    return rates.length ? rates.reduce((a, b) => a + b, 0) / rates.length : 0;
  }
}
