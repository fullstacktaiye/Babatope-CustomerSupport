<html>
<head>
  <title>List of Tickets</title>
</head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>
<c:if test="${role == 'admin'}">
  <a href="<c:url value='/sessions'/>">View Sessions</a>
</c:if>
<h2>List of Tickets</h2>
<c:if test="${role == 'admin'}">
  <p>Welcome, admin user!</p>
</c:if>
<a href="<c:url value='/TicketServlet/create'/>">Create Ticket</a><br><br>
<c:choose>
  <c:when test="${ticketDB.size() == 0}">
    <p>There are no tickets yet...</p>
  </c:when>
  <c:otherwise>
    <c:forEach var="ticketEntry" items="${ticketDB}">
      Ticket#<c:out value="${ticketEntry.id}"/>:&nbsp;
      <a href="<c:url value='/TicketServlet/view/${ticketEntry.id}'/>">
        <c:out value="${ticketEntry.customerName}"/></a><br>
    </c:forEach>
  </c:otherwise>
</c:choose>
</body>
</html>
