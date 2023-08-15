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
<h3>${ticket.subject}</h3>
<p>Customer Name: ${ticket.customerName}</p>
<p>Body: ${ticket.body}</p> 

<c:if test="${ticket.hasAttachment()}">
    <a href="<c:url value='/TicketServlet/${ticketId}/attachment/${ticket.attachment.name}'/>">
        <c:out value="${ticket.attachment.name}"/></a>
</c:if>

<br><a href="<c:url value='/TicketServlet/list'/>">Return to ticket list</a>


</body>
</html>
