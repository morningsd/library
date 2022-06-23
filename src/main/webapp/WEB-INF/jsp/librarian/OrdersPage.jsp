<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Orders"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <c:choose>
    <c:when test="${empty reserveList}">
        <div class="text-center">
            <h2><fmt:message key="orders_jsp.title.no_books_ordered" /></h2>
        </div>
    </c:when>
    <c:otherwise>
    <div class="container">
        <ul class="list-group list-group-flush">
            <c:forEach var="reserve" items="${reserveList}">
                <div class="row">
                    <div class="col-8">
                        <li class="list-group-item mb-2">
                            <div class="d-flex w-100 justify-content-between">
                                <p class="mb-1">
                                    <i class="bi bi-file-earmark-person">${reserve.account.firstName}</i>&nbsp;
                                    <i class="bi bi-briefcase">${reserve.account.lastName}</i>&nbsp;
                                    <i class="bi bi-calendar-date">${reserve.account.email}</i>&nbsp;
                                </p>
                                <i class="bi bi-basket3"><fmt:message key="orders_jsp.reservelist.created_date" />: ${reserve.createdDate}</i>
                            </div>
                            <hr>
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1"><i class="bi bi-book">${reserve.book.name}</i></h5>
                            </div>
                            <div class="d-flex w-100 justify-content-between">
                                <p class="mb-1">
                                    <i class="bi bi-file-earmark-person">${reserve.book.author}</i>&nbsp;
                                    <i class="bi bi-briefcase">${reserve.book.publisher}</i>&nbsp;
                                    <i class="bi bi-calendar-date">${reserve.book.publishedDate}</i>&nbsp;
                                    <i class="bi bi-briefcase">${reserve.book.status}</i>
                                </p>
                                <c:if test="${not empty reserve.fine and reserve.fine.compareTo(BigDecimal.ZERO) ne 0}">
                                    <i class="bi bi-basket3"><fmt:message key="orders_jsp.reservelist.fine" />: <fmt:formatNumber value="${reserve.fine}" minFractionDigits="0"/></i>
                                </c:if>
                                    <%-- <i class="bi bi-basket3">Final date: ${reserve.finalDate}</i>--%>
                            </div>
                        </li>
                    </div>
                    <div class="col-4">
                        <div class="btn-group" role="group" aria-label="Basic example">
                            <form class="form-inline mr-3" method="post" action="">
                                <input type="hidden" name="action" value="subscribe">
                                <input type="hidden" name="book_id" value="${reserve.book.id}">
                                <input type="hidden" name="book_status" value="SUBSCRIPTION">
                                <button type="submit" class="btn btn-outline-primary"><fmt:message key="orders_jsp.reservelist.subscription" /></button>
                            </form>
                            <form class="form-inline mr-3" method="post" action="">
                                <input type="hidden" name="action" value="subscribe">
                                <input type="hidden" name="book_id" value="${reserve.book.id}">
                                <input type="hidden" name="book_status" value="READING_ROOM">
                                <button type="submit" class="btn btn-outline-primary"><fmt:message key="orders_jsp.reservelist.reading_room"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </ul>
    </div>
</div>
</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src=" https:
                //code.jquery.com/jquery-3.5.1.slim.min.js"
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