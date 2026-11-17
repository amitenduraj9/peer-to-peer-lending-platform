<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - P2P Lending Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-box">
    <h2>Create Account</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <input type="text" name="name" placeholder="Full Name" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <select name="role" required>
            <option value="">-- Select Role --</option>
            <option value="BORROWER">Borrower</option>
            <option value="LENDER">Lender</option>
            <option value="ADMIN">Admin (for demo/testing)</option>
        </select>
        <button type="submit">Register</button>
    </form>

    <div class="small-link">
        Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a>
    </div>
</div>
</body>
</html>
