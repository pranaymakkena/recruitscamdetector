package com.trusthire.repository;

import com.trusthire.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Optional<Employer> findByCompanyNameIgnoreCase(String companyName);

    List<Employer> findByVerificationStatus(String status);

    List<Employer> findByCompanyNameContainingIgnoreCase(String name);
}
