<%--@elvariable id="reader" type="edu.demian.model.entity.Account"--%>
<%--@elvariable id="reserveList" type="java.util.List"--%>
<%--@elvariable id="BigDecimal" type="java.math.BigDecimal"--%>
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
                <h2>${reader.firstName} ${reader.lastName} <fmt:message
                        key="subscriptions_jsp.title.no_books_ordered"/></h2>
            </div>
        </c:when>
        <c:otherwise>
            <%--            <ul class="list-group list-group-flush">--%>
            <div class="row row-cols-1 row-cols-md-3">
                <c:forEach var="reserve" items="${reserveList}">
                    <div class="col mb-4">
                        <div class="card h-100">
                            <div class="card-body">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="card-title justify-content-between"><i
                                            class="bi bi-book">${reserve.book.name}</i></h5>
                                    <c:if test="${not empty reserve.fine}">
                                        <small style="color: red"><i class="bi bi-basket3"><fmt:message
                                                key="cabinet_jsp.reservelist.fine"/>:
                                            <fmt:formatNumber value="${reserve.fine}" minFractionDigits="0"/></i>
                                        </small>
                                    </c:if>
                                </div>
                                <p class="card-text">
                                    <i class="bi bi-file-earmark-person">${reserve.book.author}</i><br>
                                    <i class="bi bi-briefcase">${reserve.book.publisher}</i><br>
                                    <i class="bi bi-calendar-date">${reserve.book.publishedDate}</i><br>
                                    <i class="bi bi-calendar-date">${reserve.book.status}</i><br>
                                </p>
                                <form method="post" style="margin: 1rem 0 1rem 0" action="">
                                    <input type="hidden" name="action" value="returnBook">
                                    <input type="hidden" name="reserve_id" value="${reserve.id}">
                                    <input type="hidden" name="book_id" value="${reserve.book.id}">
                                    <c:choose>
                                        <c:when test="${reserve.book.status eq 'IN_STOCK' or reserve.active == 'false'}">
                                            <button type="submit" class="btn btn-outline-primary" disabled><fmt:message
                                                    key="orders_jsp.reservelist.return"/></button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="btn btn-outline-primary"><fmt:message
                                                    key="orders_jsp.reservelist.return"/></button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </div>
                            <div class="card-footer">
                                <small class="text-muted justify-content-between">
                                    <i class="bi bi-basket3"><fmt:message
                                            key="cabinet_jsp.reservelist.created_date"/>: ${reserve.createdDate}</i>
                                    <br>
                                    <i class="bi bi-basket3"><fmt:message
                                            key="cabinet_jsp.reservelist.final_date"/>: ${reserve.finalDate}</i>
                                    <c:if test="${not empty reserve.submittedDate}">
                                        <i class="bi bi-basket3"><fmt:message
                                                key="subscriptions_jsp.reservelist.submitted_date"/>:&nbsp;${reserve.submittedDate}</i>
                                    </c:if>
                                </small>
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
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