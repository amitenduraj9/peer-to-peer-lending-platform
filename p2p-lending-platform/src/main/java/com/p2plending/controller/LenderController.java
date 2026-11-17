package com.p2plending.controller;

import com.p2plending.entity.Investment;
import com.p2plending.entity.LoanApplication;
import com.p2plending.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/lender")
public class LenderController {

    private final LoanService loanService;

    @Autowired
    public LenderController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long lenderId = (Long) session.getAttribute("loggedInUserId");
        List<LoanApplication> approvedLoans = loanService.listApprovedLoans();
        List<Investment> myInvestments = loanService.listInvestmentsByLender(lenderId);

        model.addAttribute("approvedLoans", approvedLoans);
        model.addAttribute("myInvestments", myInvestments);
        model.addAttribute("userName", session.getAttribute("loggedInUserName"));
        return "lender-dashboard";
    }

    @PostMapping("/loans/{id}/fund")
    public String fundLoan(@PathVariable Long id,
                            @RequestParam BigDecimal amount,
                            HttpSession session) {
        Long lenderId = (Long) session.getAttribute("loggedInUserId");
        loanService.fundLoan(id, lenderId, amount);
        return "redirect:/lender/dashboard";
    }
}
