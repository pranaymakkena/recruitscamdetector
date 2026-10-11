import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { JobPosting } from '../../models/job-posting.model';
import { CommunityReport } from '../../models/community-report.model';
import { DashboardStats } from '../../models/dashboard-stats.model';

@Component({
  selector: 'app-placement-cell',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './placement-cell.component.html',
  styleUrls: ['./placement-cell.component.scss']
})
export class PlacementCellComponent implements OnInit {
  stats: DashboardStats | null = null;
  flaggedJobs: JobPosting[] = [];
  pendingReports: CommunityReport[] = [];
  allJobs: JobPosting[] = [];
  loading = true;
  activeTab = 'overview';
  updateSuccess = '';

  constructor(private api: ApiService) {}

  ngOnInit() { this.loadAll(); }

  loadAll() {
    this.loading = true;

    this.api.getStats().subscribe({ next: (s) => { this.stats = s; } });

    this.api.getFlaggedJobs().subscribe({
      next: (j) => { this.flaggedJobs = j; }
    });

    this.api.getAllReports().subscribe({
      next: (r) => { this.pendingReports = r.filter(x => x.status === 'PENDING'); }
    });

    this.api.getAllJobs().subscribe({
      next: (j) => { this.allJobs = j; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  verifyReport(id: number) {
    this.api.updateReportStatus(id, 'VERIFIED').subscribe({
      next: () => {
        this.updateSuccess = 'Report marked as verified.';
        this.pendingReports = this.pendingReports.filter(r => r.id !== id);
        setTimeout(() => this.updateSuccess = '', 3000);
      }
    });
  }

  dismissReport(id: number) {
    this.api.updateReportStatus(id, 'DISMISSED').subscribe({
      next: () => {
        this.updateSuccess = 'Report dismissed.';
        this.pendingReports = this.pendingReports.filter(r => r.id !== id);
        setTimeout(() => this.updateSuccess = '', 3000);
      }
    });
  }

  deleteJob(id: number) {
    this.api.deleteJob(id).subscribe({
      next: () => {
        this.flaggedJobs = this.flaggedJobs.filter(j => j.id !== id);
        this.updateSuccess = 'Job posting removed.';
        setTimeout(() => this.updateSuccess = '', 3000);
      }
    });
  }

  getRiskClass(level: string) { return 'badge badge-' + level.toLowerCase(); }
  getStatusClass(status: string) { return 'badge badge-' + (status || 'pending').toLowerCase(); }

  getScoreBarClass(score: number) {
    if (score >= 70) return 'critical';
    if (score >= 45) return 'high';
    if (score >= 20) return 'medium';
    return 'low';
  }

  getScamLabel(type: string) {
    const map: Record<string,string> = {
      PAYMENT_REQUEST: '💰 Payment Request', FAKE_COMPANY: '🎭 Fake Company',
      IDENTITY_THEFT: '🔐 Identity Theft', DATA_COLLECTION: '📋 Data Collection', OTHER: '⚠️ Other'
    };
    return map[type] || type;
  }

  formatDate(d: string | undefined) {
    if (!d) return '—';
    return new Date(d).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
  }

  get jobsByRisk() {
    const levels = ['CRITICAL','HIGH','MEDIUM','LOW'];
    return levels.map(l => ({
      level: l,
      jobs: this.allJobs.filter(j => j.riskLevel === l)
    })).filter(g => g.jobs.length > 0);
  }
}
