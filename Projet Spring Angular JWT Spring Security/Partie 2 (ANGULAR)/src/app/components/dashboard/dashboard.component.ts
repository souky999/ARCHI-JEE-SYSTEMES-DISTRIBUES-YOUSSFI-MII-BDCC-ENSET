import { Component } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
  // Config Doughnut
  public doughnutChartOptions: ChartConfiguration['options'] = { 
    responsive: true, 
    maintainAspectRatio: false,
    plugins: { legend: { position: 'bottom' } }
  };
  public doughnutChartData: ChartData<'doughnut', number[], string | string[]> = {
    labels: ['Comptes Courants', 'Comptes Épargne'],
    datasets: [{ 
      data: [65, 35], 
      backgroundColor: ['#3B82F6', '#10B981'],
      hoverBackgroundColor: ['#2563EB', '#059669']
    }]
  };

  // Config Line
  public lineChartOptions: ChartConfiguration['options'] = { 
    responsive: true, 
    maintainAspectRatio: false,
    scales: { y: { beginAtZero: true } }
  };
  public lineChartData: ChartData<'line'> = {
    labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin'],
    datasets: [
      { data: [120, 190, 150, 220, 280, 310], label: 'Entrées (€)', borderColor: '#10B981', tension: 0.3, fill: true, backgroundColor: 'rgba(16, 185, 129, 0.1)' },
      { data: [90, 120, 180, 140, 160, 200], label: 'Sorties (€)', borderColor: '#EF4444', tension: 0.3, fill: true, backgroundColor: 'rgba(239, 68, 68, 0.1)' }
    ]
  };
}