<html>
<head>
    <title>Ticket #<c:out value="${ticketId}"/></title>
</head>
<body>
<h2>Ticket Information</h2>
<h3>${ticket.getSubject()}</h3>
<p>Customer Name: ${ticket.getCustomerName()}</p>
<p>Body: ${ticket.getBody()}</p>

<c:if test="${not empty ticket.getAttachments()}">
    <h4>Attachments:</h4>
    <c:forEach var="attachment" items="${ticket.getAttachments()}">
        <a href="TicketServlet?action=download&amp;ticketId=${ticketId}&amp;attachmentId=${attachment.key}">
                ${attachment.value.getName()}</a><br>
    </c:forEach>
</c:if>

<br><a href="TicketServlet">Return to ticket list</a>


</body>
</html>
