package com.p2plending.service;

import com.p2plending.entity.*;
import com.p2plending.repository.InvestmentRepository;
import com.p2plending.repository.LoanApplicationRepository;
import com.p2plending.repository.TransactionRepository;
import com.p2plending.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoanService {

    private final LoanApplicationRepository loanRepo;
    private final InvestmentRepository investmentRepo;
    private final TransactionRepository transactionRepo;
    private final UserRepository userRepo;
    private final RepaymentService repaymentService;

    @Autowired
    public LoanService(LoanApplicationRepository loanRepo,
                        InvestmentRepository investmentRepo,
                        TransactionRepository transactionRepo,
                        UserRepository userRepo,
                        RepaymentService repaymentService) {
        this.loanRepo = loanRepo;
        this.investmentRepo = investmentRepo;
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
        this.repaymentService = repaymentService;
    }

    public LoanApplication applyForLoan(Long borrowerId, BigDecimal amount, String purpose, Integer tenureMonths) {
        User borrower = userRepo.findById(borrowerId)
                .orElseThrow(() -> new IllegalArgumentException("Borrower not found"));

        LoanApplication loan = new LoanApplication();
        loan.setBorrower(borrower);
        loan.setAmount(amount);
        loan.setPurpose(purpose);
        loan.setTenureMonths(tenureMonths);
        loan.setStatus(LoanStatus.PENDING);
        return loanRepo.save(loan);
    }

    public List<LoanApplication> listLoansByBorrower(Long borrowerId) {
        return loanRepo.findByBorrowerIdOrderByCreatedAtDesc(borrowerId);
    }

    public List<LoanApplication> listPendingLoans() {
        return loanRepo.findByStatusOrderByCreatedAtDesc(LoanStatus.PENDING);
    }

    public List<LoanApplication> listApprovedLoans() {
        return loanRepo.findByStatusOrderByCreatedAtDesc(LoanStatus.APPROVED);
    }

    public LoanApplication approveLoan(Long loanId) {
        LoanApplication loan = getLoanOrThrow(loanId);
        loan.setStatus(LoanStatus.APPROVED);
        return loanRepo.save(loan);
    }

    public LoanApplication rejectLoan(Long loanId) {
        LoanApplication loan = getLoanOrThrow(loanId);
        loan.setStatus(LoanStatus.REJECTED);
        return loanRepo.save(loan);
    }

    @Transactional
    public LoanApplication fundLoan(Long loanId, Long lenderId, BigDecimal investAmount) {
        LoanApplication loan = getLoanOrThrow(loanId);
        User lender = userRepo.findById(lenderId)
                .orElseThrow(() -> new IllegalArgumentException("Lender not found"));

        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Loan is not approved for funding");
        }

        Investment investment = new Investment();
        investment.setLoanApplication(loan);
        investment.setLender(lender);
        investment.setAmount(investAmount);
        investmentRepo.save(investment);

        // record transactions for both parties
        saveTransaction(lender, TransactionType.DEBIT, investAmount, "Funded loan #" + loan.getId());
        saveTransaction(loan.getBorrower(), TransactionType.CREDIT, investAmount, "Loan #" + loan.getId() + " funded");

        loan.setStatus(LoanStatus.FUNDED);
        loanRepo.save(loan);

        // generate the EMI repayment schedule (Module 5: JPA writes, Module 3: business logic layer)
        repaymentService.generateSchedule(loan);

        return loan;
    }

    public List<Investment> listInvestmentsByLender(Long lenderId) {
        return investmentRepo.findByLenderIdOrderByInvestedAtDesc(lenderId);
    }

    private void saveTransaction(User user, TransactionType type, BigDecimal amount, String description) {
        Transaction txn = new Transaction();
        txn.setUser(user);
        txn.setType(type);
        txn.setAmount(amount);
        txn.setDescription(description);
        transactionRepo.save(txn);
    }

    private LoanApplication getLoanOrThrow(Long loanId) {
        return loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found: " + loanId));
    }
}
