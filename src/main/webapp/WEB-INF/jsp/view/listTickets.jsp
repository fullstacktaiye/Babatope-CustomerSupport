<html>
<head>
  <title>List of Tickets</title>
</head>
<body>
<h2>List of Tickets</h2>

<c:if test="${not empty ticketMap}">
  <table>
    <tr>
      <th>Ticket ID</th>
      <th>Customer Name</th>
      <th>Subject</th>
      <th>Body</th>
    </tr>
    <c:forEach var="ticketEntry" items="${ticketMap}">
      <c:set var="ticketId" value="${ticketEntry.key}" />
      <c:set var="ticket" value="${ticketEntry.value}" />
      <tr>
        <td>${ticketId}</td>
        <td>${ticket.getCustomerName()}</td>
        <td>${ticket.getSubject()}</td>
        <td>${ticket.getBody()}</td>
        <td>
          <a href="<c:url value='TicketServlet' >
                            <c:param name='action' value='view' />
                            <c:param name='id' value='${ticketId}' />
                        </c:url>">View</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<br><a href="TicketServlet?action=createTicketForm">Create Ticket</a>
</body>
</html>