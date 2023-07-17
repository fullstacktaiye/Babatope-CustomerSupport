<%--
  Created by IntelliJ IDEA.
  User: taiye
  Date: 7/17/23
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h2>Sign Up</h2>
<form method="post" action="login">
    <input type="hidden" name="action" value="signup">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <input type="submit" value="Sign Up">
    </div>
</form>
<% if(request.getAttribute("signupFailed") != null && (boolean) request.getAttribute("signupFailed")) { %>
<p style="color: red;">Sign up failed. Please try again.</p>
<% } %>

</body>
</html>
