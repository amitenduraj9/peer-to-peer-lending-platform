<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <h2>P2P Lending — Admin</h2>
    <div>
        <span>Hi, ${userName}</span>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>

<div class="container">
    <div class="stat-grid">
        <div class="stat-box">
            <div class="num">${totalUsers}</div>
            <div class="label">Total Users</div>
        </div>
        <div class="stat-box">
            <div class="num">&#8377; ${totalDisbursed}</div>
            <div class="label">Total Disbursed</div>
        </div>
        <div class="stat-box">
            <div class="num">&#8377; ${totalCollected}</div>
            <div class="label">EMIs Collected</div>
        </div>
    </div>

    <div class="card">
        <h3>Pending Loan Applications</h3>
        <table>
            <tr>
                <th>ID</th><th>Borrower</th><th>Amount</th><th>Purpose</th><th>Tenure</th><th>Action</th>
            </tr>
            <c:forEach var="loan" items="${pendingLoans}">
                <tr>
                    <td>#${loan.id}</td>
                    <td>${loan.borrower.name}</td>
                    <td>&#8377; ${loan.amount}</td>
                    <td>${loan.purpose}</td>
                    <td>${loan.tenureMonths} months</td>
                    <td>
                        <form class="inline" method="post" action="${pageContext.request.contextPath}/admin/loans/${loan.id}/approve">
                            <button type="submit">Approve</button>
                        </form>
                        <form class="inline" method="post" action="${pageContext.request.contextPath}/admin/loans/${loan.id}/reject">
                            <button type="submit" class="reject">Reject</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty pendingLoans}">
                <tr><td colspan="6">No pending loan applications.</td></tr>
            </c:if>
        </table>
    </div>

    <div class="card">
        <h3>Loan Status Breakdown</h3>
        <table>
            <tr><th>Status</th><th>Count</th></tr>
            <c:forEach var="entry" items="${statusCounts}">
                <tr>
                    <td><span class="badge badge-${entry.key}">${entry.key}</span></td>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
