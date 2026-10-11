import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { CommunityReport } from '../../models/community-report.model';

@Component({
  selector: 'app-community-reports',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './community-reports.component.html',
  styleUrls: ['./community-reports.component.scss']
})
export class CommunityReportsComponent implements OnInit {
  reports: CommunityReport[] = [];
  filtered: CommunityReport[] = [];
  loading = true;
  showForm = false;
  success = '';
  error = '';
  filterStatus = 'ALL';
  filterType = 'ALL';

  form: CommunityReport = {
    reporterName: '', reporterEmail: '', college: '',
    companyName: '', jobTitle: '', description: '',
    scamType: 'PAYMENT_REQUEST'
  };

  scamTypes = [
    { value: 'PAYMENT_REQUEST', label: '💰 Payment Request' },
    { value: 'FAKE_COMPANY',    label: '🎭 Fake Company' },
    { value: 'IDENTITY_THEFT',  label: '🔐 Identity Theft' },
    { value: 'DATA_COLLECTION', label: '📋 Data Collection' },
    { value: 'OTHER',           label: '⚠️ Other' },
  ];

  constructor(private api: ApiService) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.api.getAllReports().subscribe({
      next: (r) => { this.reports = r; this.applyFilter(); this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  applyFilter() {
    this.filtered = this.reports.filter(r => {
      const statusOk = this.filterStatus === 'ALL' || r.status === this.filterStatus;
      const typeOk   = this.filterType   === 'ALL' || r.scamType === this.filterType;
      return statusOk && typeOk;
    });
  }

  submit() {
    if (!this.form.reporterName || !this.form.companyName || !this.form.description) {
      this.error = 'Please fill in required fields.';
      return;
    }
    this.error = '';
    this.api.submitReport(this.form).subscribe({
      next: () => {
        this.success = 'Report submitted successfully! It will be reviewed by our team.';
        this.showForm = false;
        this.resetForm();
        this.load();
      },
      error: () => { this.error = 'Submission failed. Please try again.'; }
    });
  }

  upvote(id: number) {
    this.api.upvoteReport(id).subscribe({
      next: (updated) => {
        const idx = this.reports.findIndex(r => r.id === id);
        if (idx !== -1) this.reports[idx] = updated;
        this.applyFilter();
      }
    });
  }

  resetForm() {
    this.form = {
      reporterName: '', reporterEmail: '', college: '',
      companyName: '', jobTitle: '', description: '',
      scamType: 'PAYMENT_REQUEST'
    };
  }

  getStatusClass(status: string) { return 'badge badge-' + (status || 'pending').toLowerCase(); }
  getScamLabel(type: string) {
    return this.scamTypes.find(s => s.value === type)?.label || type;
  }
  formatDate(d: string | undefined) {
    if (!d) return '—';
    return new Date(d).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
  }
}
