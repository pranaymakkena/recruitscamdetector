package com.trusthire.controller;

import com.trusthire.dto.DashboardStats;
import com.trusthire.service.CommunityReportService;
import com.trusthire.service.EmployerService;
import com.trusthire.service.JobPostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final JobPostingService jobService;
    private final CommunityReportService reportService;
    private final EmployerService employerService;

    public DashboardController(JobPostingService jobService,
                               CommunityReportService reportService,
                               EmployerService employerService) {
        this.jobService = jobService;
        this.reportService = reportService;
        this.employerService = employerService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getStats() {
        DashboardStats stats = new DashboardStats(
            jobService.countAll(),
            jobService.countHighRisk(),
            jobService.countFlagged(),
            reportService.countAll(),
            employerService.countVerified(),
            employerService.countBlacklisted(),
            jobService.averageRiskScore() != null ? jobService.averageRiskScore() : 0.0,
            reportService.countVerified()
        );
        return ResponseEntity.ok(stats);
    }
}
