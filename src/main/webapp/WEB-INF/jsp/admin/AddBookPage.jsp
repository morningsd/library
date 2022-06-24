<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Add book"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <form action="" method="post">
        <input type="hidden" name="action" value="addBook">
        <div class="form-group">
            <label for="name"><fmt:message key="addbook_jsp.label.name"/>:</label>
            <input type="text" class="form-control" placeholder="<fmt:message key="addbook_jsp.placeholder.name" />"
                   id="name" name="name" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="author"><fmt:message key="addbook_jsp.label.author"/>:</label>
            <input type="text" class="form-control" placeholder="<fmt:message key="addbook_jsp.placeholder.author" />"
                   id="author" name="author" autocomplete="off">
        </div>
        <div class="form-group">
            <label for="publisher"><fmt:message key="addbook_jsp.label.publisher"/>:</label>
            <input type="text" class="form-control" placeholder="<fmt:message key="addbook_jsp.placeholder.publisher" />"
                   id="publisher" name="publisher" autocomplete="off">
        </div>
        <div class="row form-group">
            <div class="col">
                <label for="published_date"><fmt:message key="addbook_jsp.label.published_date"/>:</label>
                <input type="text" class="form-control datepicker" id="published_date"
                       placeholder="<fmt:message key="addbook_jsp.placeholder.published_date" />"
                       name="published_date" autocomplete="off">
            </div>
            <div class="col">
                <label for="quantity"><fmt:message key="addbook_jsp.label.quantity"/>:</label>
                <input type="number" class="form-control" id="quantity" name="quantity" min="0" step="1"
                       placeholder="<fmt:message key="addbook_jsp.placeholder.quantity" />"
                       autocomplete="off">
            </div>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="addbook_jsp.button.add"/></button>
    </form>


    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
</body>
<!-- this should go after your </body> -->
<link rel="stylesheet" type="text/css" href="/css/jquery.datetimepicker.css"/>
<script src="/js/jquery.js"></script>
<script src="/js/jquery.datetimepicker.full.min.js"></script>
<script>
    jQuery.noConflict();
    jQuery('.datepicker').datetimepicker({
        timepicker: false,
        format: 'd/m/Y'
    });
</script>
</html>