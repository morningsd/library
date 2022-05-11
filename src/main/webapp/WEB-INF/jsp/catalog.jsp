<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Catalog"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="row">
        <div class="col-md-3">
            <form class="search_form" id="search_form" method="post" action="/jsp/search">
                <input class="form-control form-control-sm search_box" type="search"
                       placeholder="<fmt:message key="home_jsp.placeholder.search"/>"
                       name="search_data" value="${searchData}"
                       aria-label="Search" autocomplete="off">
                <button class="btn btn-outline-secondary btn-sm btn-block" type="submit">
                    <i class="bi bi-search" aria-hidden="true"><fmt:message key="home_jsp.button.search"/></i>
                </button>

                <button type="button" class="btn btn-outline-info btn-sm btn-block" data-toggle="collapse" data-target="#search_feed" aria-expanded="false" aria-controls="search_feed">
                    <fmt:message key="home_jsp.button.refine"/>
                    <span class="caret"></span>
                </button>

                <div id="search_feed" class="collapse">
                    <hr>
                    <%--     SORT BY    --%>
                    <h5><fmt:message key="catalog_jsp.search.search"/></h5>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="search_by" id="by_name_id" value="NAME" checked>
                        <label class="form-check-label" for="by_name_id">
                            <fmt:message key="catalog_jsp.search.by_name" />
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="search_by" id="by_author_id" value="AUTHOR">
                        <label class="form-check-label" for="by_author_id">
                            <fmt:message key="catalog_jsp.search.by_author" />
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="search_by" id="by_publisher" value="PUBLISHER">
                        <label class="form-check-label" for="by_publisher">
                            <fmt:message key="catalog_jsp.search.by_publisher" />
                        </label>
                    </div>
                    <hr>
                    <%--         NAME      --%>
                    <h4><fmt:message key="catalog_jsp.sort.name"/></h4>
                    <select class="custom-select" name="sort_name">
                        <c:choose>
                            <c:when test="${nameOrder eq 'ASC'}">
                                <option value="ASC" selected>
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC">
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="ASC">
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC" selected>
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <hr>
                    <%--         AUTHOR      --%>
                    <h4><fmt:message key="catalog_jsp.sort.author"/></h4>
                    <select class="custom-select" name="sort_author">
                        <c:choose>
                            <c:when test="${authorOrder eq 'ASC'}">
                                <option value="ASC" selected>
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC">
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="ASC">
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC" selected>
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <hr>
                    <%--         PUBLISHER      --%>
                    <h4><fmt:message key="catalog_jsp.sort.publisher"/></h4>
                    <select class="custom-select" name="sort_publisher">
                        <c:choose>
                            <c:when test="${publisherOrder eq 'ASC'}">
                                <option value="ASC" selected>
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC">
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="ASC">
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC" selected>
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <hr>
                    <%--    DATE   --%>
                    <h4><fmt:message key="catalog_jsp.sort.date"/></h4>
                    <select class="custom-select" name="sort_date">
                        <c:choose>
                            <c:when test="${publishedDateOrder eq 'ASC'}">
                                <option value="ASC" selected>
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC">
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="ASC">
                                    <fmt:message key="catalog_jsp.sort.ascending"/>&#8593;
                                </option>
                                <option value="DESC" selected>
                                    <fmt:message key="catalog_jsp.sort.descending"/>&#8595;
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <hr>

                </div>
            </form>
        </div>
        <div class="col-md-9">
            <c:if test="${not empty bookList}">
                <ul class="list-group list-group-flush">
                    <c:forEach var="book" items="${bookList}">
                        <li class="list-group-item">
                            <i class="bi bi-book">${book.name}</i>&nbsp;
                            <i class="bi bi-file-earmark-person">${book.author}</i>&nbsp;
                            <i class="bi bi-briefcase">${book.publisher}</i>&nbsp;
                            <i class="bi bi-calendar-date">${book.publishedDate}</i>&nbsp;
                            <i class="bi bi-basket3">${book.quantity} in stock</i>
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