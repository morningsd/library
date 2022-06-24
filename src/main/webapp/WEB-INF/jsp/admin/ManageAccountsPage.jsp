<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Users"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <c:if test="${empty accountList}">
        <div class="text-center">
            <h2><fmt:message key="manageaccounts_jsp.title.no_users_registered" /></h2>
        </div>
    </c:if>
    <c:if test="${not empty accountList}">

        <ul class="list-group list-group-flush">
            <c:forEach var="account" items="${accountList}">
                <li class="list-group-item">
                    <i class="bi bi-file-earmark-person"><fmt:message key="manageaccounts_jsp.accountlist.fname" />: "${account.firstName}"</i>&nbsp;
                    <fmt:message key="manageaccounts_jsp.accountlist.lname" />: "${account.lastName}"&nbsp;
                    <fmt:message key="manageaccounts_jsp.accountlist.email" />: "${account.email}"&nbsp;
                    <fmt:message key="manageaccounts_jsp.accountlist.is_admin"/>: "${account.admin}"&nbsp;
                    <fmt:message key="manageaccounts_jsp.accountlist.is_blocked" />: "${account.blocked}"&nbsp;
                    <c:choose>
                        <c:when test="${account.blocked == 'true'}">
                            <form class="form-inline" method="post" action="">
                                <input type="hidden" name="action" value="unblock">
                                <input type="hidden" name="account_id" value="${account.id}">
                                <button type="submit" class="btn btn-outline-primary"><fmt:message
                                        key="manageaccounts_jsp.accountlist.button.unblock"/></button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form class="form-inline" method="post" action="">
                                <input type="hidden" name="action" value="block">
                                <input type="hidden" name="account_id" value="${account.id}">
                                <button type="submit" class="btn btn-outline-secondary"><fmt:message
                                        key="manageaccounts_jsp.accountlist.button.block"/></button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </li>
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