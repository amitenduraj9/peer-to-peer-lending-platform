package com.p2plending.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A plain Servlet Filter (Module 1: Servlets - Session Tracking using
 * HttpSession) that protects /borrower/**, /lender/** and /admin/** URLs.
 * If the user is not logged in (no session attribute), they are redirected
 * to the login page. If logged in but wrong role, they are redirected to
 * their own dashboard.
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        HttpSession session = request.getSession(false);
        Object userId = (session != null) ? session.getAttribute("loggedInUserId") : null;
        Object role = (session != null) ? session.getAttribute("loggedInUserRole") : null;

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String roleStr = String.valueOf(role);
        if (path.startsWith("/borrower") && !"BORROWER".equals(roleStr)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (path.startsWith("/lender") && !"LENDER".equals(roleStr)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (path.startsWith("/admin") && !"ADMIN".equals(roleStr)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        chain.doFilter(req, res);
    }
}
