package com.p2plending.repository;

import com.p2plending.entity.LoanApplication;
import com.p2plending.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByBorrowerIdOrderByCreatedAtDesc(Long borrowerId);
    List<LoanApplication> findByStatusOrderByCreatedAtDesc(LoanStatus status);
}
