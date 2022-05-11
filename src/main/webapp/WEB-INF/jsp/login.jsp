<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <form action="/jsp/login" method="post" name="login">
        <div class="form-group">
            <label for="email"><fmt:message key="login_jsp.label.email"/>:</label>
            <input type="email" class="form-control" placeholder="<fmt:message key="login_jsp.placeholder.email"/>" id="email" name="email">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="login_jsp.label.password"/>:</label>
            <input type="password" class="form-control" placeholder="<fmt:message key="login_jsp.placeholder.password"/>" id="password" name="password">
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="login_jsp.button.login"/></button>
    </form>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>

<script src="/js/jquery-3.6.0.min.js" />
<script src="/js/bootstrap.min.js" />
</body>
</html>