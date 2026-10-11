package com.trusthire.repository;

import com.trusthire.model.CommunityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityReportRepository extends JpaRepository<CommunityReport, Long> {

    List<CommunityReport> findByStatus(String status);

    List<CommunityReport> findByCompanyNameContainingIgnoreCase(String companyName);

    List<CommunityReport> findByScamType(String scamType);

    @Query("SELECT r FROM CommunityReport r ORDER BY r.upvotes DESC")
    List<CommunityReport> findAllOrderedByUpvotes();

    @Query("SELECT COUNT(r) FROM CommunityReport r WHERE r.status = 'VERIFIED'")
    long countVerified();
}
