package com.trusthire.controller;

import com.trusthire.dto.JobPostingRequest;
import com.trusthire.model.JobPosting;
import com.trusthire.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobPostingController {

    private final JobPostingService service;

    public JobPostingController(JobPostingService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public ResponseEntity<JobPosting> analyzeJob(@Valid @RequestBody JobPostingRequest request) {
        return ResponseEntity.ok(service.submitAndAnalyze(request));
    }

    @GetMapping
    public ResponseEntity<List<JobPosting>> getAllJobs() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJob(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/risk/{level}")
    public ResponseEntity<List<JobPosting>> getByRisk(@PathVariable String level) {
        return ResponseEntity.ok(service.getByRiskLevel(level));
    }

    @GetMapping("/flagged")
    public ResponseEntity<List<JobPosting>> getFlagged() {
        return ResponseEntity.ok(service.getFlagged());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
