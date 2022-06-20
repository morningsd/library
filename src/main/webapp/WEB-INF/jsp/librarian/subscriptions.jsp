<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Subscription"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <c:choose>
        <c:when test="${empty reserveList}">
            <div class="text-center">
                <h2>${reader.firstName} ${reader.lastName} <fmt:message key="subscriptions_jsp.title.no_books_ordered" /></h2>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="list-group list-group-flush">
                <c:forEach var="reserve" items="${reserveList}">
                    <li class="list-group-item">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1"><i class="bi bi-book">${reserve.book.name}</i></h5>
                            <i class="bi bi-basket3"><fmt:message key="subscriptions_jsp.reservelist.active" />: ${reserve.active}</i>
                            <i class="bi bi-basket3"><fmt:message key="subscriptions_jsp.reservelist.created_date" />: ${reserve.createdDate}</i>
                        </div>
                        <div class="d-flex w-100 justify-content-between">
                            <p class="mb-1">
                                <i class="bi bi-file-earmark-person">${reserve.book.author}</i>&nbsp;
                                <i class="bi bi-briefcase">${reserve.book.publisher}</i>&nbsp;
                                <i class="bi bi-calendar-date">${reserve.book.publishedDate}</i>
                            </p>
                            <c:if test="${not empty reserve.fine and reserve.fine.compareTo(BigDecimal.ZERO) ne 0}">
                                <i class="bi bi-basket3"><fmt:message key="subscriptions_jsp.reservelist.fine" />: <fmt:formatNumber value="${reserve.fine}" minFractionDigits="0"/></i>
                            </c:if>
                            <i class="bi bi-basket3"><fmt:message key="subscriptions_jsp.reservelist.submitted_date" />: ${reserve.submittedDate}</i>
                            <i class="bi bi-basket3"><fmt:message key="subscriptions_jsp.reservelist.final_date" />: ${reserve.finalDate}</i>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"
        integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s"
        crossorigin="anonymous"></script>
</body>
</html>