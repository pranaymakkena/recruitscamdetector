import { Component, NgZone, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { JobPosting, JobPostingRequest } from '../../models/job-posting.model';

@Component({
  selector: 'app-analyze-job',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './analyze-job.component.html',
  styleUrls: ['./analyze-job.component.scss']
})
export class AnalyzeJobComponent {
  form: JobPostingRequest = {
    title: '', company: '', location: '', description: '',
    contactEmail: '', sourceUrl: '', submittedBy: ''
  };

  result: JobPosting | null = null;
  loading = false;
  error = '';
  riskFactorsList: string[] = [];
  progress = 0;
  progressLabel = 'Initialising...';
  private progressTimer: ReturnType<typeof setInterval> | null = null;
  private progressTimeouts: ReturnType<typeof setTimeout>[] = [];

  constructor(private api: ApiService, private ngZone: NgZone, private cdr: ChangeDetectorRef) {}

  submit() {
    if (!this.form.title || !this.form.company || !this.form.description) {
      this.error = 'Please fill in the required fields: Title, Company, and Description.';
      return;
    }
    this.error = '';
    this.loading = true;
    this.result = null;
    const startTime = Date.now();
    this.startProgress();

    this.api.analyzeJob(this.form).subscribe({
      next: (res) => {
        const elapsed = Date.now() - startTime;
        const delay = Math.max(0, 2500 - elapsed);
        setTimeout(() => {
          this.ngZone.run(() => {
            this.progress = 100;
            this.progressLabel = 'Complete!';
            this.stopProgress();
            setTimeout(() => {
              this.ngZone.run(() => {
                this.result = res;
                this.riskFactorsList = res.riskFactors ? res.riskFactors.split('\n').filter(f => f.trim()) : [];
                this.loading = false;
                this.cdr.detectChanges();
                document.getElementById('result-section')?.scrollIntoView({ behavior: 'smooth' });
              });
            }, 300);
          });
        }, delay);
      },
      error: () => {
        this.ngZone.run(() => {
          this.stopProgress();
          this.error = 'Backend connection failed. Make sure the Spring Boot server is running on port 8080.';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  private startProgress() {
    this.progress = 5;
    this.progressLabel = 'Initialising...';
    this.progressTimeouts = [];
    const steps = [
      { at: 200,  pct: 15, label: 'Scanning keywords...' },
      { at: 500,  pct: 35, label: 'Checking email domain...' },
      { at: 900,  pct: 55, label: 'Detecting scam patterns...' },
      { at: 1300, pct: 72, label: 'Calculating risk score...' },
      { at: 1700, pct: 88, label: 'Generating AI analysis...' },
      { at: 2200, pct: 95, label: 'Finalising report...' },
    ];
    steps.forEach(s => {
      const t = setTimeout(() => {
        this.ngZone.run(() => {
          if (this.loading) { this.progress = s.pct; this.progressLabel = s.label; }
        });
      }, s.at);
      this.progressTimeouts.push(t);
    });
  }

  private stopProgress() {
    this.progressTimeouts.forEach(t => clearTimeout(t));
    this.progressTimeouts = [];
    if (this.progressTimer) { clearInterval(this.progressTimer); this.progressTimer = null; }
  }

  reset() {
    this.form = { title: '', company: '', location: '', description: '', contactEmail: '', sourceUrl: '', submittedBy: '' };
    this.result = null;
    this.error = '';
    this.progress = 0;
    this.progressLabel = 'Initialising...';
    this.progressTimeouts = [];
  }

  getRiskClass(level: string) {
    return 'badge badge-' + level.toLowerCase();
  }

  getScoreBarClass(score: number) {
    if (score >= 70) return 'critical';
    if (score >= 45) return 'high';
    if (score >= 20) return 'medium';
    return 'low';
  }

  getRiskEmoji(level: string) {
    const map: Record<string, string> = { LOW: '✅', MEDIUM: '⚠️', HIGH: '🔴', CRITICAL: '🚨' };
    return map[level] || '❓';
  }

  getRiskMessage(level: string) {
    const map: Record<string, string> = {
      LOW: 'This job posting appears relatively safe. Always verify independently.',
      MEDIUM: 'Some suspicious patterns found. Verify before applying.',
      HIGH: 'Multiple red flags detected. Exercise extreme caution.',
      CRITICAL: 'This is very likely a SCAM. Do NOT apply or share personal details.'
    };
    return map[level] || '';
  }

  loadExample(type: 'safe' | 'scam') {
    if (type === 'safe') {
      this.form = {
        title: 'Software Engineer - Java Backend',
        company: 'Infosys Limited',
        location: 'Bengaluru, Karnataka',
        description: 'We are looking for a Java Backend Engineer with 0-2 years experience. Work on enterprise applications using Spring Boot and microservices. Package: 5-7 LPA. Apply through official careers portal only. Interview process includes written test and technical rounds.',
        contactEmail: 'campus@infosys.com',
        sourceUrl: 'https://infosys.com/careers',
        submittedBy: 'Placement Officer'
      };
    } else {
      this.form = {
        title: 'URGENT HIRING - Work from Home - Earn 5000 Daily',
        company: 'DIGITAL JOBS PVT LTD',
        location: 'Work From Anywhere',
        description: 'Earn 5000 to 50000 daily from home! No experience needed. Guaranteed job offer within 24 hours. Pay registration fee of Rs.500 to get started. Contact on WhatsApp immediately. Limited seats available. 100% genuine offer.',
        contactEmail: 'quickjobs2024@gmail.com',
        sourceUrl: 'https://t.me/quickjobs',
        submittedBy: 'Anonymous'
      };
    }
  }
}
