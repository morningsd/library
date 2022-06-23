<%--@elvariable id="accountRole" type="java.lang.String"--%>
<%--@elvariable id="account" type="edu.demian.model.entity.Account"--%>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Cabinet"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="mb-3 mt-3">
        <h3 class="text-center"><fmt:message key="cabinet_jsp.title.user"/>: ${account.firstName} ${account.lastName}
            (<fmt:message key="cabinet_jsp.title.email"/>: ${account.email}) ${accountRole}</h3>
    </div>
    <c:if test="${accountRole eq 'READER'}">
        <c:choose>
            <%--@elvariable id="reserveList" type="java.util.List"--%>
            <c:when test="${empty reserveList}">
                <div class="text-center">
                    <h2><fmt:message key="cabinet_jsp.reservelist.have_not_ordered"/></h2>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-3">
                    <c:forEach var="reserve" items="${reserveList}">
                        <div class="col mb-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title"><i class="bi bi-book">${reserve.book.name}</i></h5>
                                    <p class="card-text">
                                        <i class="bi bi-file-earmark-person">${reserve.book.author}</i><br>
                                        <i class="bi bi-briefcase">${reserve.book.publisher}</i><br>
                                        <i class="bi bi-calendar-date">${reserve.book.publishedDate}</i><br>
                                    </p>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted justify-content-between"><i class="bi bi-basket3"><fmt:message
                                            key="cabinet_jsp.reservelist.created_date"/>: ${reserve.createdDate}</i><i
                                            class="bi bi-basket3"><fmt:message
                                            key="cabinet_jsp.reservelist.final_date"/>: ${reserve.finalDate}</i></small>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
<%--                <ul class="list-group list-group-flush">--%>
<%--                    <c:forEach var="reserve" items="${reserveList}">--%>
<%--                        <li class="list-group-item">--%>
<%--                            <div class="d-flex w-100 justify-content-between">--%>
<%--                                <h5 class="mb-1"><i class="bi bi-book">${reserve.book.name}</i></h5>--%>
<%--                                <i class="bi bi-basket3"><fmt:message--%>
<%--                                        key="cabinet_jsp.reservelist.created_date"/>: ${reserve.createdDate}</i>--%>
<%--                            </div>--%>
<%--                            <div class="d-flex w-100 justify-content-between">--%>
<%--                                <p class="mb-1">--%>
<%--                                    <i class="bi bi-file-earmark-person">${reserve.book.author}</i>&nbsp;--%>
<%--                                    <i class="bi bi-briefcase">${reserve.book.publisher}</i>&nbsp;--%>
<%--                                    <i class="bi bi-calendar-date">${reserve.book.publishedDate}</i>--%>
<%--                                </p>--%>
<%--                                    &lt;%&ndash;@elvariable id="BigDecimal" type="java.math.BigDecimal"&ndash;%&gt;--%>
<%--                                <c:if test="${not empty reserve.fine and reserve.fine.compareTo(BigDecimal.ZERO) ne 0}">--%>
<%--                                    <i class="bi bi-basket3"><fmt:message key="cabinet_jsp.reservelist.fine"/>:--%>
<%--                                        <fmt:formatNumber value="${reserve.fine}" minFractionDigits="0"/></i>--%>
<%--                                </c:if>--%>
<%--                                <i class="bi bi-basket3"><fmt:message--%>
<%--                                        key="cabinet_jsp.reservelist.final_date"/>: ${reserve.finalDate}</i>--%>
<%--                            </div>--%>
<%--                        </li>--%>
<%--                    </c:forEach>--%>
<%--                </ul>--%>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:if test="${accountRole eq 'LIBRARIAN'}">
        <div class="container">
            <div class="row">
                <div class="col">
                    <a class="btn btn-outline-info form-control btn-block"
                       href="${pageContext.request.contextPath}/jsp/librarian/readers"><fmt:message
                            key="cabinet_jsp.readers.button"/></a>
                </div>
                <div class="col">
                    <a class="btn btn-outline-primary form-control btn-block"
                       href="${pageContext.request.contextPath}/jsp/librarian/orders"><fmt:message
                            key="cabinet_jsp.orders.button"/></a>
                </div>
            </div>
        </div>
    </c:if>
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