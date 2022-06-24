<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Error"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container text-center">
    <img src="${pageContext.request.contextPath}/img/404.jpg" style="margin: 0 auto" alt="404 image">
    <h2>You can return to the <a href="/jsp/home">home page</a></h2>
</div>
</body>
</html>