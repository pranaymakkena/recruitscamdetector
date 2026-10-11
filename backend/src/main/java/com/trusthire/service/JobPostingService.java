package com.trusthire.service;

import com.trusthire.dto.AnalysisResult;
import com.trusthire.dto.JobPostingRequest;
import com.trusthire.model.JobPosting;
import com.trusthire.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobPostingService {

    private final JobPostingRepository repository;
    private final FraudAnalysisService analysisService;

    public JobPostingService(JobPostingRepository repository, FraudAnalysisService analysisService) {
        this.repository = repository;
        this.analysisService = analysisService;
    }

    public JobPosting submitAndAnalyze(JobPostingRequest request) {
        JobPosting posting = new JobPosting();
        posting.setTitle(request.getTitle());
        posting.setCompany(request.getCompany());
        posting.setLocation(request.getLocation());
        posting.setDescription(request.getDescription());
        posting.setContactEmail(request.getContactEmail());
        posting.setSourceUrl(request.getSourceUrl());
        posting.setSubmittedBy(request.getSubmittedBy());

        // Run AI analysis
        AnalysisResult result = analysisService.analyze(
            request.getTitle(),
            request.getCompany(),
            request.getDescription(),
            request.getContactEmail()
        );

        posting.setRiskScore(result.getRiskScore());
        posting.setRiskLevel(result.getRiskLevel());
        posting.setRiskFactors(String.join("\n", result.getRiskFactors()));
        posting.setAiAnalysis(result.getAiAnalysis());
        posting.setAnalyzedAt(LocalDateTime.now());
        posting.setFlaggedAsFraud(result.getRiskScore() >= 70);

        return repository.save(posting);
    }

    public List<JobPosting> getAll() {
        return repository.findAllOrderedByDate();
    }

    public Optional<JobPosting> getById(Long id) {
        return repository.findById(id);
    }

    public List<JobPosting> getByRiskLevel(String riskLevel) {
        return repository.findByRiskLevel(riskLevel.toUpperCase());
    }

    public List<JobPosting> getFlagged() {
        return repository.findByFlaggedAsFraudTrue();
    }

    public long countHighRisk() {
        return repository.countHighRisk();
    }

    public long countFlagged() {
        return repository.countFlagged();
    }

    public Double averageRiskScore() {
        return repository.averageRiskScore();
    }

    public long countAll() {
        return repository.count();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
