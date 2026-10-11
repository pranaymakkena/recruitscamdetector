package com.trusthire.controller;

import com.trusthire.model.CommunityReport;
import com.trusthire.service.CommunityReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class CommunityReportController {

    private final CommunityReportService service;

    public CommunityReportController(CommunityReportService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CommunityReport> submitReport(@RequestBody CommunityReport report) {
        return ResponseEntity.ok(service.submit(report));
    }

    @GetMapping
    public ResponseEntity<List<CommunityReport>> getAllReports() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityReport> getReport(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CommunityReport>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<CommunityReport> upvote(@PathVariable Long id) {
        return ResponseEntity.ok(service.upvote(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CommunityReport> updateStatus(
        @PathVariable Long id,
        @RequestBody Map<String, String> body
    ) {
        String status = body.get("status");
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
