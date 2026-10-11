import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobPosting, JobPostingRequest } from '../models/job-posting.model';
import { CommunityReport } from '../models/community-report.model';
import { Employer } from '../models/employer.model';
import { DashboardStats } from '../models/dashboard-stats.model';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private base = '/api';

  constructor(private http: HttpClient) {}

  // ── Dashboard ────────────────────────────────────────────────────────────
  getStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.base}/dashboard/stats`);
  }

  // ── Job Postings ─────────────────────────────────────────────────────────
  analyzeJob(req: JobPostingRequest): Observable<JobPosting> {
    return this.http.post<JobPosting>(`${this.base}/jobs/analyze`, req);
  }

  getAllJobs(): Observable<JobPosting[]> {
    return this.http.get<JobPosting[]>(`${this.base}/jobs`);
  }

  getJobById(id: number): Observable<JobPosting> {
    return this.http.get<JobPosting>(`${this.base}/jobs/${id}`);
  }

  getJobsByRisk(level: string): Observable<JobPosting[]> {
    return this.http.get<JobPosting[]>(`${this.base}/jobs/risk/${level}`);
  }

  getFlaggedJobs(): Observable<JobPosting[]> {
    return this.http.get<JobPosting[]>(`${this.base}/jobs/flagged`);
  }

  deleteJob(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/jobs/${id}`);
  }

  // ── Community Reports ────────────────────────────────────────────────────
  submitReport(report: CommunityReport): Observable<CommunityReport> {
    return this.http.post<CommunityReport>(`${this.base}/reports`, report);
  }

  getAllReports(): Observable<CommunityReport[]> {
    return this.http.get<CommunityReport[]>(`${this.base}/reports`);
  }

  upvoteReport(id: number): Observable<CommunityReport> {
    return this.http.post<CommunityReport>(`${this.base}/reports/${id}/upvote`, {});
  }

  updateReportStatus(id: number, status: string): Observable<CommunityReport> {
    return this.http.put<CommunityReport>(`${this.base}/reports/${id}/status`, { status });
  }

  // ── Employers ────────────────────────────────────────────────────────────
  getAllEmployers(): Observable<Employer[]> {
    return this.http.get<Employer[]>(`${this.base}/employers`);
  }

  searchEmployers(q: string): Observable<Employer[]> {
    const params = new HttpParams().set('q', q);
    return this.http.get<Employer[]>(`${this.base}/employers/search`, { params });
  }

  addEmployer(employer: Employer): Observable<Employer> {
    return this.http.post<Employer>(`${this.base}/employers`, employer);
  }

  verifyEmployer(id: number, status: string, notes: string): Observable<Employer> {
    return this.http.put<Employer>(`${this.base}/employers/${id}/verify`, { status, notes });
  }
}
