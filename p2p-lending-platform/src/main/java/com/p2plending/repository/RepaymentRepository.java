package com.p2plending.repository;

import com.p2plending.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> findByLoanApplicationIdOrderByDueDateAsc(Long loanId);
}
