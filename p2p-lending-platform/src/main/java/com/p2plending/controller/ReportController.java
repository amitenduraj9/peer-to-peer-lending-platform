package com.p2plending.controller;

import com.p2plending.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Pure REST API (JSON) example on top of the same raw-JDBC ReportService.
 * Try: http://localhost:8080/api/reports/summary
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        BigDecimal disbursed = reportService.getTotalDisbursedAmount();
        BigDecimal collected = reportService.getTotalRepaymentsCollected();

        summary.put("totalUsers", reportService.getTotalUsers());
        summary.put("totalDisbursed", disbursed);
        summary.put("totalCollected", collected);
        summary.put("loanStatusCounts", reportService.getLoanStatusCounts());
        return summary;
    }
}
