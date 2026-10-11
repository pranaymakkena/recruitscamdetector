package com.trusthire.service;

import com.trusthire.model.CommunityReport;
import com.trusthire.repository.CommunityReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityReportService {

    private final CommunityReportRepository repository;

    public CommunityReportService(CommunityReportRepository repository) {
        this.repository = repository;
    }

    public CommunityReport submit(CommunityReport report) {
        return repository.save(report);
    }

    public List<CommunityReport> getAll() {
        return repository.findAllOrderedByUpvotes();
    }

    public Optional<CommunityReport> getById(Long id) {
        return repository.findById(id);
    }

    public List<CommunityReport> getByStatus(String status) {
        return repository.findByStatus(status.toUpperCase());
    }

    public CommunityReport upvote(Long id) {
        return repository.findById(id).map(report -> {
            report.setUpvotes(report.getUpvotes() + 1);
            return repository.save(report);
        }).orElseThrow(() -> new RuntimeException("Report not found: " + id));
    }

    public CommunityReport updateStatus(Long id, String status) {
        return repository.findById(id).map(report -> {
            report.setStatus(status.toUpperCase());
            return repository.save(report);
        }).orElseThrow(() -> new RuntimeException("Report not found: " + id));
    }

    public long countAll() {
        return repository.count();
    }

    public long countVerified() {
        return repository.countVerified();
    }
}
