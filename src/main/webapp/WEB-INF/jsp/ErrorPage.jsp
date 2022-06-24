<%--@elvariable id="errorMessage" type="java.lang.String"--%>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Error"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container text-center">
    <img src="${pageContext.request.contextPath}/img/error.jpg" style="margin: 0 auto; width: 800px; height: 600px" alt="error image">
    <c:choose>
        <c:when test = "${not empty errorMessage}">
            <h2>${errorMessage}</h2>
        </c:when>
        <c:otherwise>
            <h1>Something went wrong :(</h1>
        </c:otherwise>
    </c:choose>
    <p>You can return to the <a href="/jsp/home">home page</a></p>
</div>
</body>
</html>