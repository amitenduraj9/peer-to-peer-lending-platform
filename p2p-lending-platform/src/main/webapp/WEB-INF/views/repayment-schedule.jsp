<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Repayment Schedule</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
    <h2>P2P Lending — EMI Schedule (Loan #${loanId})</h2>
    <div>
        <a href="${pageContext.request.contextPath}/borrower/dashboard">Back to Dashboard</a>
    </div>
</div>

<div class="container">
    <div class="card">
        <table>
            <tr><th>EMI #</th><th>Amount</th><th>Due Date</th><th>Status</th><th>Action</th></tr>
            <c:forEach var="r" items="${repayments}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>&#8377; ${r.emiAmount}</td>
                    <td>${r.dueDate}</td>
                    <td><span class="badge badge-${r.status}">${r.status}</span></td>
                    <td>
                        <c:if test="${r.status == 'PENDING'}">
                            <form method="post" action="${pageContext.request.contextPath}/borrower/repayments/${r.id}/pay">
                                <input type="hidden" name="loanId" value="${loanId}">
                                <button type="submit">Pay Now</button>
                            </form>
                        </c:if>
                        <c:if test="${r.status == 'PAID'}">
                            Paid on ${r.paidDate}
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
