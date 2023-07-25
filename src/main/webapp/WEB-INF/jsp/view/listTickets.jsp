<html>
<head>
  <title>List of Tickets</title>
</head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>
<h2>List of Tickets</h2>
<a href="<c:url value='/TicketServlet/create'/>">Create Ticket</a><br><br>
<c:choose>
  <c:when test="${ticketMap.size() == 0}">
    <p>There are no tickets yet...</p>
  </c:when>
  <c:otherwise>
    <c:forEach var="ticketEntry" items="${ticketMap}">
      Ticket#<c:out value="${ticketEntry.key}"/>:&nbsp;
      <a href="<c:url value='/TicketServlet/view/${ticketEntry.key}'/>">
        <c:out value="${ticketEntry.value.customerName}"/></a><br>
    </c:forEach>
  </c:otherwise>
</c:choose>
</body>
</html>
