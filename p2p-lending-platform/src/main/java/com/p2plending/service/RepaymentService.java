package com.p2plending.service;

import com.p2plending.entity.LoanApplication;
import com.p2plending.entity.Repayment;
import com.p2plending.entity.RepaymentStatus;
import com.p2plending.repository.RepaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class RepaymentService {

    private final RepaymentRepository repaymentRepo;

    @Autowired
    public RepaymentService(RepaymentRepository repaymentRepo) {
        this.repaymentRepo = repaymentRepo;
    }

    /** Generates a simple flat-EMI schedule once a loan is funded. */
    public void generateSchedule(LoanApplication loan) {
        int months = loan.getTenureMonths() == null ? 1 : loan.getTenureMonths();
        BigDecimal principal = loan.getAmount();
        double rate = loan.getInterestRate() == null ? 10.0 : loan.getInterestRate();

        BigDecimal totalPayable = principal.add(
                principal.multiply(BigDecimal.valueOf(rate / 100.0))
        );
        BigDecimal emi = totalPayable.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        for (int i = 0; i < months; i++) {
            Repayment repayment = new Repayment();
            repayment.setLoanApplication(loan);
            repayment.setEmiAmount(emi);
            repayment.setDueDate(dueDate.plusMonths(i));
            repayment.setStatus(RepaymentStatus.PENDING);
            repaymentRepo.save(repayment);
        }
    }

    public List<Repayment> listByLoan(Long loanId) {
        return repaymentRepo.findByLoanApplicationIdOrderByDueDateAsc(loanId);
    }

    public Repayment payEmi(Long repaymentId) {
        Repayment repayment = repaymentRepo.findById(repaymentId)
                .orElseThrow(() -> new IllegalArgumentException("Repayment not found: " + repaymentId));
        repayment.setStatus(RepaymentStatus.PAID);
        repayment.setPaidDate(LocalDate.now());
        return repaymentRepo.save(repayment);
    }
}
