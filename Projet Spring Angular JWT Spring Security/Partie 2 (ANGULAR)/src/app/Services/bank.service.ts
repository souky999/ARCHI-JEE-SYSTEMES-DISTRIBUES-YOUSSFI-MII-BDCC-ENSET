import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BankService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getCustomers(): Observable<any[]> { return this.http.get<any[]>(`${this.apiUrl}/customers`); }
  getAccounts(customerId: number): Observable<any[]> { return this.http.get<any[]>(`${this.apiUrl}/accounts/customer/${customerId}`); }
  getAccountHistory(accountId: string): Observable<any[]> { return this.http.get<any[]>(`${this.apiUrl}/accounts/${accountId}/operations`); }
  // ... autres méthodes (credit, debit, transfer)
}