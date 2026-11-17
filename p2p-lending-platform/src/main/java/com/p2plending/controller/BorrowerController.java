package com.p2plending.controller;

import com.p2plending.entity.LoanApplication;
import com.p2plending.entity.Repayment;
import com.p2plending.service.LoanService;
import com.p2plending.service.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/borrower")
public class BorrowerController {

    private final LoanService loanService;
    private final RepaymentService repaymentService;

    @Autowired
    public BorrowerController(LoanService loanService, RepaymentService repaymentService) {
        this.loanService = loanService;
        this.repaymentService = repaymentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long borrowerId = (Long) session.getAttribute("loggedInUserId");
        List<LoanApplication> loans = loanService.listLoansByBorrower(borrowerId);
        model.addAttribute("loans", loans);
        model.addAttribute("userName", session.getAttribute("loggedInUserName"));
        return "borrower-dashboard";
    }

    @PostMapping("/loans/apply")
    public String applyLoan(@RequestParam BigDecimal amount,
                             @RequestParam String purpose,
                             @RequestParam Integer tenureMonths,
                             HttpSession session) {
        Long borrowerId = (Long) session.getAttribute("loggedInUserId");
        loanService.applyForLoan(borrowerId, amount, purpose, tenureMonths);
        return "redirect:/borrower/dashboard";
    }

    @GetMapping("/loans/{id}/repayments")
    public String viewRepayments(@PathVariable Long id, Model model) {
        List<Repayment> repayments = repaymentService.listByLoan(id);
        model.addAttribute("repayments", repayments);
        model.addAttribute("loanId", id);
        return "repayment-schedule";
    }

    @PostMapping("/repayments/{id}/pay")
    public String payEmi(@PathVariable Long id, @RequestParam Long loanId) {
        repaymentService.payEmi(id);
        return "redirect:/borrower/loans/" + loanId + "/repayments";
    }
}
