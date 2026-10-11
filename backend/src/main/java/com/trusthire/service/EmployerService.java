package com.trusthire.service;

import com.trusthire.model.Employer;
import com.trusthire.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {

    private final EmployerRepository repository;

    public EmployerService(EmployerRepository repository) {
        this.repository = repository;
    }

    public Employer save(Employer employer) {
        return repository.save(employer);
    }

    public List<Employer> getAll() {
        return repository.findAll();
    }

    public Optional<Employer> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<Employer> findByName(String name) {
        return repository.findByCompanyNameIgnoreCase(name);
    }

    public List<Employer> search(String query) {
        return repository.findByCompanyNameContainingIgnoreCase(query);
    }

    public List<Employer> getByStatus(String status) {
        return repository.findByVerificationStatus(status.toUpperCase());
    }

    public long countVerified() {
        return repository.findByVerificationStatus("VERIFIED").size();
    }

    public long countBlacklisted() {
        return repository.findByVerificationStatus("BLACKLISTED").size();
    }

    public Employer updateStatus(Long id, String status, String notes) {
        return repository.findById(id).map(emp -> {
            emp.setVerificationStatus(status.toUpperCase());
            emp.setVerificationNotes(notes);
            if ("VERIFIED".equals(status.toUpperCase())) emp.setTrustScore(85);
            else if ("BLACKLISTED".equals(status.toUpperCase())) emp.setTrustScore(5);
            else if ("SUSPICIOUS".equals(status.toUpperCase())) emp.setTrustScore(25);
            return repository.save(emp);
        }).orElseThrow(() -> new RuntimeException("Employer not found: " + id));
    }
}
