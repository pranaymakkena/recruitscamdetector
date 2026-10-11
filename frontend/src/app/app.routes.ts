import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AnalyzeJobComponent } from './components/analyze-job/analyze-job.component';
import { CommunityReportsComponent } from './components/community-reports/community-reports.component';
import { EmployerVerifyComponent } from './components/employer-verify/employer-verify.component';
import { PlacementCellComponent } from './components/placement-cell/placement-cell.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard',       component: DashboardComponent },
  { path: 'analyze',         component: AnalyzeJobComponent },
  { path: 'reports',         component: CommunityReportsComponent },
  { path: 'employers',       component: EmployerVerifyComponent },
  { path: 'placement-cell',  component: PlacementCellComponent },
  { path: '**', redirectTo: '/dashboard' }
];
