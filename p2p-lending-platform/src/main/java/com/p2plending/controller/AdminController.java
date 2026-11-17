package com.p2plending.controller;

import com.p2plending.entity.LoanApplication;
import com.p2plending.service.LoanService;
import com.p2plending.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LoanService loanService;
    private final ReportService reportService;

    @Autowired
    public AdminController(LoanService loanService, ReportService reportService) {
        this.loanService = loanService;
        this.reportService = reportService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        List<LoanApplication> pendingLoans = loanService.listPendingLoans();

        model.addAttribute("pendingLoans", pendingLoans);
        model.addAttribute("userName", session.getAttribute("loggedInUserName"));

        // Values below come straight from raw JDBC / JdbcTemplate queries (ReportService)
        model.addAttribute("totalDisbursed", reportService.getTotalDisbursedAmount());
        model.addAttribute("totalUsers", reportService.getTotalUsers());
        model.addAttribute("totalCollected", reportService.getTotalRepaymentsCollected());
        model.addAttribute("statusCounts", reportService.getLoanStatusCounts());

        return "admin-dashboard";
    }

    @PostMapping("/loans/{id}/approve")
    public String approve(@PathVariable Long id) {
        loanService.approveLoan(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/loans/{id}/reject")
    public String reject(@PathVariable Long id) {
        loanService.rejectLoan(id);
        return "redirect:/admin/dashboard";
    }
}
