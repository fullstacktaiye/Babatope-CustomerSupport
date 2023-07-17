<html>
<head>
  <title>List of Tickets</title>
</head>
<body>
<a href="<c:url value='/login'>
        <c:param name='logout'/>
    </c:url>">Logout</a>
<c:if test="${role == 'admin'}">
  <a href="<c:url value='/sessions'/>">View Sessions</a>
</c:if>
<h2>List of Tickets</h2>
<c:if test="${role == 'admin'}">
    <!-- Display specific content for admin users -->
    <p>Welcome, admin user!</p>
    <!-- Add additional content here for admin users -->
</c:if>


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