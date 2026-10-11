import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { DashboardStats } from '../../models/dashboard-stats.model';
import { JobPosting } from '../../models/job-posting.model';
import { CommunityReport } from '../../models/community-report.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  stats: DashboardStats | null = null;
  recentJobs: JobPosting[] = [];
  recentReports: CommunityReport[] = [];
  loading = true;

  riskDistribution: { label: string; count: number; color: string }[] = [];

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.loading = true;

    this.api.getStats().subscribe({
      next: (s) => { this.stats = s; },
      error: () => {}
    });

    this.api.getAllJobs().subscribe({
      next: (jobs) => {
        this.recentJobs = jobs.slice(0, 5);
        this.buildRiskDistribution(jobs);
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });

    this.api.getAllReports().subscribe({
      next: (r) => { this.recentReports = r.slice(0, 4); },
      error: () => {}
    });
  }

  buildRiskDistribution(jobs: JobPosting[]) {
    const levels = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
    const colors = ['#2E7D32', '#E65100', '#B71C1C', '#FF1744'];
    this.riskDistribution = levels.map((l, i) => ({
      label: l,
      count: jobs.filter(j => j.riskLevel === l).length,
      color: colors[i]
    }));
  }

  getRiskClass(level: string): string {
    return 'badge badge-' + level.toLowerCase();
  }

  getScoreBarClass(score: number): string {
    if (score >= 70) return 'critical';
    if (score >= 45) return 'high';
    if (score >= 20) return 'medium';
    return 'low';
  }

  getStatusClass(status: string): string {
    return 'badge badge-' + (status || 'pending').toLowerCase();
  }

  getScamTypeLabel(type: string): string {
    const map: Record<string, string> = {
      PAYMENT_REQUEST: '💰 Payment Request',
      FAKE_COMPANY: '🎭 Fake Company',
      IDENTITY_THEFT: '🔐 Identity Theft',
      DATA_COLLECTION: '📋 Data Collection',
      OTHER: '⚠️ Other'
    };
    return map[type] || type;
  }

  formatDate(date: string | undefined): string {
    if (!date) return '—';
    return new Date(date).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
  }
}
