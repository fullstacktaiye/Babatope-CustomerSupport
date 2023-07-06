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
    <form action="TicketServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="create">
        <label for="customerName">Customer Name: </label>
        <input type="text" name="customerName" id="customerName"><br>
        <label for="subject">Subject: </label>
        <input type="text" name="subject" id="subject"><br>
        <label for="body">Body: </label>
        <textarea name="body" id="body" cols="30" rows="10"></textarea><br>
        <label for="attachment">Attachment: </label>
        <input type="file" name="attachment" id="attachment"><br>
        <input type="submit" value="Create Ticket">
    </form>

</body>
</html>
