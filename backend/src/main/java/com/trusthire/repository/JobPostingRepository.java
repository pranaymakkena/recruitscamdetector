package com.trusthire.repository;

import com.trusthire.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByRiskLevel(String riskLevel);

    List<JobPosting> findByFlaggedAsFraudTrue();

    List<JobPosting> findByCompanyContainingIgnoreCase(String company);

    @Query("SELECT j FROM JobPosting j ORDER BY j.submittedAt DESC")
    List<JobPosting> findAllOrderedByDate();

    @Query("SELECT COUNT(j) FROM JobPosting j WHERE j.riskLevel = 'HIGH' OR j.riskLevel = 'CRITICAL'")
    long countHighRisk();

    @Query("SELECT COUNT(j) FROM JobPosting j WHERE j.flaggedAsFraud = true")
    long countFlagged();

    @Query("SELECT AVG(j.riskScore) FROM JobPosting j")
    Double averageRiskScore();
}
