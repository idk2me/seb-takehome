import { Routes } from '@angular/router';
import { Rates } from './rates/rates';
import { Converter } from './converter/converter';
import { History } from './history/history';

export const routes: Routes = [
  { path: '', redirectTo: '/rates', pathMatch: 'full' },
  { path: 'rates', component: Rates },
  { path: 'converter', component: Converter },
  { path: 'history/:code', component: History }
];
