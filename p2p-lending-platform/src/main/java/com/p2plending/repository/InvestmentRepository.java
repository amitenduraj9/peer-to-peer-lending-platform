package com.p2plending.repository;

import com.p2plending.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByLoanApplicationId(Long loanId);
    List<Investment> findByLenderIdOrderByInvestedAtDesc(Long lenderId);
}
