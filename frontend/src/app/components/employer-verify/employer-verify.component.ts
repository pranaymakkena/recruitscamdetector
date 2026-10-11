import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { Employer } from '../../models/employer.model';
import { VerifiedCountPipe, StatusCountPipe } from '../../services/employer-pipes.pipe';

@Component({
  selector: 'app-employer-verify',
  standalone: true,
  imports: [CommonModule, FormsModule, VerifiedCountPipe, StatusCountPipe],
  templateUrl: './employer-verify.component.html',
  styleUrls: ['./employer-verify.component.scss']
})
export class EmployerVerifyComponent implements OnInit {
  employers: Employer[] = [];
  filtered: Employer[] = [];
  loading = true;
  searchQuery = '';
  filterStatus = 'ALL';
  showAddForm = false;
  success = '';

  form: Employer = { companyName: '', website: '', industry: '', linkedinUrl: '' };

  constructor(private api: ApiService) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.api.getAllEmployers().subscribe({
      next: (e) => { this.employers = e; this.applyFilter(); this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  search() {
    if (this.searchQuery.trim().length < 2) { this.applyFilter(); return; }
    this.api.searchEmployers(this.searchQuery).subscribe({
      next: (e) => { this.filtered = e; }
    });
  }

  applyFilter() {
    this.filtered = this.employers.filter(e =>
      this.filterStatus === 'ALL' || e.verificationStatus === this.filterStatus
    );
  }

  addEmployer() {
    if (!this.form.companyName) return;
    this.api.addEmployer(this.form).subscribe({
      next: () => {
        this.success = 'Employer added successfully!';
        this.showAddForm = false;
        this.form = { companyName: '', website: '', industry: '', linkedinUrl: '' };
        this.load();
      }
    });
  }

  getStatusClass(status: string | undefined) {
    return 'badge badge-' + (status || 'unknown').toLowerCase();
  }

  getStatusIcon(status: string | undefined) {
    const map: Record<string, string> = {
      VERIFIED: '✅', SUSPICIOUS: '⚠️', BLACKLISTED: '🚫', UNKNOWN: '❓'
    };
    return map[status || 'UNKNOWN'] || '❓';
  }

  getTrustBarClass(score: number) {
    if (score >= 70) return 'trusted';
    if (score >= 40) return 'neutral';
    return 'risky';
  }

  formatDate(d: string | undefined) {
    if (!d) return '—';
    return new Date(d).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
  }
}
