<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lender Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <h2>P2P Lending — Lender</h2>
    <div>
        <span>Hi, ${userName}</span>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>

<div class="container">
    <div class="card">
        <h3>Loans Available to Fund</h3>
        <table>
            <tr>
                <th>ID</th><th>Borrower</th><th>Amount</th><th>Purpose</th><th>Tenure</th><th>Fund</th>
            </tr>
            <c:forEach var="loan" items="${approvedLoans}">
                <tr>
                    <td>#${loan.id}</td>
                    <td>${loan.borrower.name}</td>
                    <td>&#8377; ${loan.amount}</td>
                    <td>${loan.purpose}</td>
                    <td>${loan.tenureMonths} months</td>
                    <td>
                        <form class="inline" method="post" action="${pageContext.request.contextPath}/lender/loans/${loan.id}/fund">
                            <input type="number" step="0.01" name="amount" value="${loan.amount}" style="width:110px;display:inline-block" required>
                            <button type="submit">Fund</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty approvedLoans}">
                <tr><td colspan="6">No approved loans available right now.</td></tr>
            </c:if>
        </table>
    </div>

    <div class="card">
        <h3>My Investments</h3>
        <table>
            <tr><th>Loan ID</th><th>Amount Invested</th><th>Date</th></tr>
            <c:forEach var="inv" items="${myInvestments}">
                <tr>
                    <td>#${inv.loanApplication.id}</td>
                    <td>&#8377; ${inv.amount}</td>
                    <td>${inv.investedAt}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty myInvestments}">
                <tr><td colspan="3">You haven't funded any loans yet.</td></tr>
            </c:if>
        </table>
    </div>
</div>
</body>
</html>
