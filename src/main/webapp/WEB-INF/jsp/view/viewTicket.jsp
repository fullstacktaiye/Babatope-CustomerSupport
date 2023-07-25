<html>
<head>
    <title>Ticket #<c:out value="${ticketId}"/></title>
</head>
<body>
<a href= "<c:url value='/logout'/>">Logout</a>
<c:if test="${role == 'admin'}">
    <a href="<c:url value='/sessions'/>">View Sessions</a>
</c:if>
<h2>Ticket Information</h2>
<h3>${ticket.getSubject()}</h3>
<p>Customer Name: ${ticket.getCustomerName()}</p>
<p>Body: ${ticket.getBody()}</p>

<c:if test="${not empty ticket.getAttachments()}">
    <h4>Attachments:</h4>
    <c:forEach var="attachment" items="${ticket.getAttachments()}">
        <a href="<c:url value='/TicketServlet/${ticketId}/attachment/${attachment.value.name}'/>">
                ${attachment.value.getName()}</a><br>
    </c:forEach>
</c:if>

<br><a href="<c:url value='/TicketServlet/list'/>">Return to ticket list</a>


</body>
</html>
