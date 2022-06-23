<%--@elvariable id="searchData" type="java.lang.String"--%>
<%--@elvariable id="searchBy" type="java.lang.String"--%>
<%--@elvariable id="nameOrder" type="java.lang.String"--%>
<%--@elvariable id="authorOrder" type="java.lang.String"--%>
<%--@elvariable id="publisherOrder" type="java.lang.String"--%>
<%--@elvariable id="publishedDateOrder" type="java.lang.String"--%>
<%--@elvariable id="bookList" type="java.util.List"--%>
<%--@elvariable id="account" type="edu.demian.model.entity.Account"--%>
<%--@elvariable id="accountBookList" type="java.util.Collection"--%>
<%--@elvariable id="sortOrder" type="java.lang.String"--%>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Catalog"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="row">
        <div class="col-md-3">
            <form class="search_form" id="search_form" method="post" action="">
                <input type="hidden" name="action" value="search">
                <input class="form-control form-control-sm search_box" type="search"
                       placeholder="<fmt:message key="home_jsp.placeholder.search"/>"
                       name="searchData" value="${searchData}"
                       aria-label="Search" autocomplete="off">
                <button class="btn btn-outline-secondary btn-sm btn-block" type="submit">
                    <i class="bi bi-search" aria-hidden="true"><fmt:message key="home_jsp.button.search"/></i>
                </button>

                <button type="button" class="btn btn-outline-info btn-sm btn-block" data-toggle="collapse"
                        data-target="#search_feed" aria-expanded="false" aria-controls="search_feed">
                    <fmt:message key="home_jsp.button.refine"/>
                    <span class="caret"></span>
                </button>

                <div id="search_feed" class="collapse">
                    <hr>
                    <%--     SORT BY    --%>
                    <h5><fmt:message key="catalog_jsp.search.search"/></h5>
                    <div class="form-check">
                        <c:choose>
                            <c:when test="${searchBy eq 'name' || searchBy eq null}">
                                <input class="form-check-input" type="radio" name="searchBy" id="by_name_id"
                                       value="name" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="searchBy" id="by_name_id"
                                       value="name">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="by_name_id">
                            <fmt:message key="catalog_jsp.search.by_name"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <c:choose>
                            <c:when test="${searchBy eq 'author'}">
                                <input class="form-check-input" type="radio" name="searchBy" id="by_author_id"
                                       value="author" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="searchBy" id="by_author_id"
                                       value="author">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="by_author_id">
                            <fmt:message key="catalog_jsp.search.by_author"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <c:choose>
                            <c:when test="${searchBy eq 'publisher'}">
                                <input class="form-check-input" type="radio" name="searchBy" id="by_publisher"
                                       value="publisher" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="searchBy" id="by_publisher"
                                       value="publisher">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="by_publisher">
                            <fmt:message key="catalog_jsp.search.by_publisher"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <c:choose>
                            <c:when test="${searchBy eq 'published_date'}">
                                <input class="form-check-input" type="radio" name="searchBy" id="by_published_date"
                                       value="published_date" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="radio" name="searchBy" id="by_published_date"
                                       value="published_date">
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="by_published_date">
                            <fmt:message key="catalog_jsp.search.by_published_date"/>
                        </label>
                    </div>
                    <hr>
                    <%--         SORT      --%>
                    <h4><fmt:message key="catalog_jsp.sort"/></h4>
                    <select class="custom-select" name="sort">
                        <c:choose>
                            <c:when test="${sortOrder eq 'ASC'}">
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
                </div>
            </form>
        </div>
        <div class="col-md-9">
            <c:if test="${not empty bookList}">
                <ul class="list-group list-group-flush">
                    <c:forEach var="book" items="${bookList}">
                        <div class="row">
                            <div class="col-10">
                                <li class="list-group-item">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h5 class="mb-1"><i class="bi bi-book">${book.name}</i></h5>
                                        <small><i class="bi bi-basket3">${book.quantity} <fmt:message
                                                key="catalog_jsp.booklist.in_stock"/></i></small>
                                    </div>
                                    <p class="mb-1">
                                        <i class="bi bi-file-earmark-person">${book.author}</i>&nbsp;
                                        <i class="bi bi-briefcase">${book.publisher}</i>&nbsp;
                                        <i class="bi bi-calendar-date">${book.publishedDate}</i>
                                    </p>
                                </li>
                            </div>
                            <div class="col-2">
                                <c:if test="${accountRole.name ne 'librarian'}">
                                    <form class="form-inline" method="post" action="">
                                        <input type="hidden" name="action" value="orderBook">
                                        <input type="hidden" name="book_id" value="${book.id}">
                                        <c:choose>
                                            <c:when test="${book.quantity > 0 and not empty account}">
                                                <button type="submit" class="btn btn-outline-primary">
                                                    <fmt:message
                                                            key="catalog_jsp.button.order"/></button>
                                            </c:when>
                                            <c:when test="${fnc:contains(accountBookList, book)}}">
                                                <button type="submit" class="btn btn-outline-secondary"
                                                        disabled>
                                                    <fmt:message
                                                            key="catalog_jsp.button.order"/></button>
                                                <small><fmt:message
                                                        key="catalog_jsp.booklist.already_have_this_book"/></small>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" title="You need to login first"
                                                        class="btn btn-outline-secondary" disabled>
                                                    <fmt:message
                                                            key="catalog_jsp.button.order"/></button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
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