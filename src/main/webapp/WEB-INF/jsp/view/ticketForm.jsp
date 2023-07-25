<%--
  Created by IntelliJ IDEA.
  User: taiye
  Date: 7/6/23
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket Form</title>
</head>
<body>
    <h1>Ticket Form</h1>
    <form:form action="create" method="post" enctype="multipart/form-data" modelAttribute="ticket">
        <form:label path="customerName">Customer Name: </form:label>
        <form:input path="customerName" id="customerName" /><br>
        <form:label path="subject">Subject: </form:label>
        <form:input path="subject" id="subject" /><br>
        <form:label path="body">Body: </form:label>
        <form:textarea path="body" id="body" cols="30" rows="10" /><br>
        <form:label path="attachment">Attachment: </form:label>
        <form:input type="file" path="attachment" id="attachment" /><br>
        <input type="submit" value="Create Ticket">
    </form:form>

</body>
</html>
