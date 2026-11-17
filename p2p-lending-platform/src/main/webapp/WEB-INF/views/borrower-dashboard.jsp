<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Borrower Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <h2>P2P Lending — Borrower</h2>
    <div>
        <span>Hi, ${userName}</span>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>

<div class="container">
    <div class="card">
        <h3>Apply for a New Loan</h3>
        <form method="post" action="${pageContext.request.contextPath}/borrower/loans/apply">
            <input type="number" step="0.01" name="amount" placeholder="Loan Amount (₹)" required>
            <input type="text" name="purpose" placeholder="Purpose (e.g. Education, Business)" required>
            <input type="number" name="tenureMonths" placeholder="Tenure (months)" required>
            <button type="submit">Apply for Loan</button>
        </form>
    </div>

    <div class="card">
        <h3>My Loan Applications</h3>
        <table>
            <tr>
                <th>ID</th><th>Amount</th><th>Purpose</th><th>Tenure</th><th>Status</th><th>Action</th>
            </tr>
            <c:forEach var="loan" items="${loans}">
                <tr>
                    <td>#${loan.id}</td>
                    <td>&#8377; ${loan.amount}</td>
                    <td>${loan.purpose}</td>
                    <td>${loan.tenureMonths} months</td>
                    <td><span class="badge badge-${loan.status}">${loan.status}</span></td>
                    <td>
                        <c:if test="${loan.status == 'FUNDED' || loan.status == 'CLOSED'}">
                            <a href="${pageContext.request.contextPath}/borrower/loans/${loan.id}/repayments">View EMIs</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty loans}">
                <tr><td colspan="6">No loan applications yet.</td></tr>
            </c:if>
        </table>
    </div>
</div>
</body>
</html>
