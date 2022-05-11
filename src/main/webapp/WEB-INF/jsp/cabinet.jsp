<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Cabinet"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <c:choose>
        <c:when test="${not empty ticketList}">
            <h2><fmt:message key="cabinet_jsp.header.tickets" /></h2>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th><fmt:message key="cabinet_jsp.table.topic" /></th>
                    <th><fmt:message key="cabinet_jsp.table.cost" /></th>
                    <th><fmt:message key="cabinet_jsp.table.number" /></th>
                    <th><fmt:message key="cabinet_jsp.table.total" /></th>
                    <th><fmt:message key="cabinet_jsp.table.time" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ticket" items="${ticketList}">
                    <tr>
                        <td>${ticket.exposition.topic}</td>
                        <td>&#8372;${ticket.exposition.price}</td>
                        <td>${ticket.quantity}</td>
                        <td>&#8372;${ticket.totalPrice}</td>
                        <td>${ticket.creationDateFormatted}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <h2><fmt:message key="cabinet_jsp.header.notickets"/>.</h2>
            <p><fmt:message key="cabinet_jsp.choose"/> <a href="/jsp/expos" style="text-decoration: none"><fmt:message key="cabinet_jsp.here"/></a></p>
        </c:otherwise>
    </c:choose>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="/js/jquery-3.6.0.min.js" />
<script src="/js/bootstrap.min.js" />
</body>
</html>