<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Readers"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <c:if test="${empty readerList}">
        <div class="text-center">
            <h2>No readers are registered yet</h2>
        </div>
    </c:if>
    <c:if test="${not empty readerList}">
        <ul class="list-group list-group-flush">
            <c:forEach var="reader" items="${readerList}">
                <li class="list-group-item">
                    <i class="bi bi-file-earmark-person">First name: "${reader.firstName}"</i>&nbsp;
                    Last name: "${reader.lastName}"&nbsp;
                    Email: "${reader.email}"&nbsp;
                </li>
                <form class="form-inline" method="get" action="/jsp/librarian/subscriptions">
                    <input type="hidden" name="reader_id" value="${reader.id}">
                    <button type="submit" class="btn btn-outline-primary">View subscriptions</button>
                </form>
            </c:forEach>
        </ul>
    </c:if>
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