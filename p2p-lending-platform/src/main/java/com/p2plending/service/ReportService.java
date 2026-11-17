package com.p2plending.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Deliberately written using Spring's JdbcTemplate (built directly on top of
 * java.sql.Connection / PreparedStatement / ResultSet) instead of JPA.
 * This demonstrates Module 1 (JDBC) and Module 3 (Spring JDBC) from the
 * syllabus, side by side with the JPA/Hibernate repositories used elsewhere.
 */
@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Total amount disbursed across all funded loans - raw SQL aggregate query. */
    public BigDecimal getTotalDisbursedAmount() {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM investments";
        BigDecimal total = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        return total == null ? BigDecimal.ZERO : total;
    }

    /** Total registered users - raw SQL count query. */
    public long getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count == null ? 0 : count;
    }

    /** Number of loans grouped by status - raw SQL GROUP BY, mapped manually from ResultSet. */
    public Map<String, Long> getLoanStatusCounts() {
        String sql = "SELECT status, COUNT(*) AS cnt FROM loan_applications GROUP BY status";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        Map<String, Long> result = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            result.put(String.valueOf(row.get("status")), ((Number) row.get("cnt")).longValue());
        }
        return result;
    }

    /** Total EMI amount collected so far - raw SQL aggregate on repayments. */
    public BigDecimal getTotalRepaymentsCollected() {
        String sql = "SELECT COALESCE(SUM(emi_amount), 0) FROM repayments WHERE status = 'PAID'";
        BigDecimal total = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        return total == null ? BigDecimal.ZERO : total;
    }
}
