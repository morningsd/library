<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Register"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <form action="/jsp/register" method="post" name="registration">
        <div class="row form-group">
            <div class="col">
                <label for="fname"><fmt:message key="register_jsp.label.fname" />:</label>
                <input type="text" class="form-control" id="fname" placeholder="<fmt:message key="register_jsp.placeholder.fname" />" name="fname">
            </div>
            <div class="col">
                <label for="lname"><fmt:message key="register_jsp.label.lname"/>:</label>
                <input type="text" class="form-control" id="lname" placeholder="<fmt:message key="register_jsp.placeholder.lname" />" name="lname">
            </div>
        </div>
        <div class="form-group">
            <label for="email"><fmt:message key="register_jsp.label.email" />:</label>
            <input type="email" class="form-control" placeholder="<fmt:message key="register_jsp.placeholder.email" />" id="email" name="email">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="register_jsp.label.password" />:</label>
            <input type="password" class="form-control" placeholder="<fmt:message key="register_jsp.placeholder.password" />" id="password" name="password">
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="register_jsp.button.register"/></button>
    </form>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
</body>
</html>