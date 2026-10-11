import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  menuOpen = false;

  navLinks = [
    { path: '/dashboard',       label: 'Dashboard',       icon: '📊' },
    { path: '/analyze',         label: 'Analyze Job',     icon: '🔍' },
    { path: '/reports',         label: 'Community',       icon: '🚨' },
    { path: '/employers',       label: 'Employers',       icon: '🏢' },
    { path: '/placement-cell',  label: 'Placement Cell',  icon: '🎓' },
  ];
}
