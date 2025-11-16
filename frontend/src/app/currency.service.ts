import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface Currency {
  code: string;
}

export interface ConversionRequest {
  from: string;
  to: string;
  amount: number;
}

export interface ConversionResult {
  convertedAmount: number;
  rate: number;
  date: string;
}

export interface ExchangeRate {
  currency: string;
  rate: number;
}

export interface LatestRatesResponse {
  date: string;
  rates: ExchangeRate[];
}

export interface HistoryPoint {
  date: string;
  rate: number;
}

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getCurrencies(): Observable<Currency[]> {
    return this.http.get<Currency[]>(`${this.apiUrl}/debug/currencies`);
  }

  convert(request: ConversionRequest): Observable<ConversionResult> {
    return this.http.post<ConversionResult>(`${this.apiUrl}/convert`, request);
  }

  getLatestRates(): Observable<LatestRatesResponse> {
    return this.http.get<LatestRatesResponse>(`${this.apiUrl}/rates/latest`);
  }

  getHistory(code: string, from: string, to: string): Observable<HistoryPoint[]> {
    return this.http.get<HistoryPoint[]>(`${this.apiUrl}/rates/${code}/history`, {
      params: { from, to }
    });
  }
}
