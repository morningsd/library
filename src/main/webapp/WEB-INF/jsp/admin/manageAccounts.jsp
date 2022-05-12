<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Users"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="row">
        <div class="col-md-9">
            <c:if test="${empty accountList}">
                <div class="text-center">
                    <h2>No users are registered right now</h2>
                </div>
            </c:if>
            <c:if test="${not empty accountList}">
                <ul class="list-group list-group-flush">
                    <c:forEach var="account" items="${accountList}">
                        <li class="list-group-item">
                            <i class="bi bi-file-earmark-person">First name: "${account.firstName}"</i>&nbsp;
                            Last name: "${account.lastName}"&nbsp;
                            Email: "${account.email}"&nbsp;
                            Is admin: "${account.admin}"&nbsp;
                            Is blocked: "${account.blocked}"&nbsp;
                            <form class="form-inline" method="post" action="/jsp/admin/blockAccount">
                                <input type="hidden" name="account_id" value="${account.id}">
                                <button type="submit" class="btn btn-outline-secondary"><fmt:message
                                        key="manageAccounts_jsp.account.button.block"/></button>
                            </form>
                            <form class="form-inline" method="post" action="/jsp/admin/unblockAccount">
                                <input type="hidden" name="account_id" value="${account.id}">
                                <button type="submit" class="btn btn-outline-secondary"><fmt:message
                                        key="manageAccounts_jsp.account.button.unblock"/></button>
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
</body>
</html>