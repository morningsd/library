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
                <button class="btn btn-outline-secondary btn-sm btn-block" type="submit"><i class="bi bi-search"
                                                                                            aria-hidden="true"><fmt:message
                        key="home_jsp.button.search"/></i>
                </button>

                <button type="button" class="btn btn-outline-info btn-sm btn-block" data-toggle="collapse"
                        data-target="#search_feed"><fmt:message key="home_jsp.button.refine"/><span
                        class="caret"></span>
                </button>

                <div id="search_feed" class="collapse in">
                    <hr>
                    <div class="list-group list-group">
                        <h4><fmt:message key="home_jsp.header.categories"/></h4>
                        <c:forEach var="category" items="${categoryList}">
                            <div class="list-group-item">
<%--                                <c:choose>--%>
<%--                                    <c:when test="${sessionScope.lang eq 'ua'}">--%>
<%--                                        <c:choose>--%>
<%--                                            <c:when test="${fnc:contains(checkedCategoryList, category)}">--%>
<%--                                                <input type="checkbox" id="category${category.id}"--%>
<%--                                                       name="search_categories"--%>
<%--                                                       value="${category.id}" checked>--%>
<%--                                                <label for="category${category.id}">${category.nameRu}</label>--%>
<%--                                            </c:when>--%>
<%--                                            <c:otherwise>--%>
<%--                                                <input type="checkbox" id="category${category.id}"--%>
<%--                                                       name="search_categories"--%>
<%--                                                       value="${category.id}">--%>
<%--                                                <label for="category${category.id}">${category.nameRu}</label>--%>
<%--                                            </c:otherwise>--%>
<%--                                        </c:choose>--%>
<%--                                    </c:when>--%>
<%--                                    <c:otherwise>--%>
<%--                                        <c:choose>--%>
<%--                                            <c:when test="${fnc:contains(checkedCategoryList, category)}">--%>
<%--                                                <input type="checkbox" id="category${category.id}"--%>
<%--                                                       name="search_categories"--%>
<%--                                                       value="${category.id}" checked>--%>
<%--                                                <label for="category${category.id}">${category.nameEn}</label>--%>
<%--                                            </c:when>--%>
<%--                                            <c:otherwise>--%>
<%--                                                <input type="checkbox" id="category${category.id}"--%>
<%--                                                       name="search_categories"--%>
<%--                                                       value="${category.id}">--%>
<%--                                                <label for="category${category.id}">${category.nameEn}</label>--%>
<%--                                            </c:otherwise>--%>
<%--                                        </c:choose>--%>
<%--                                    </c:otherwise>--%>
<%--                                </c:choose>--%>
                            </div>
                        </c:forEach>
                    </div>
                    <hr>
                    <h4><fmt:message key="expos_jsp.search.date"/></h4>
                    <select class="custom-select" name="search_date">
                        <c:choose>
                            <c:when test="${dateOrder eq 'ASC'}">
                                <option value="ASC" selected><fmt:message
                                        key="home_jsp.select.ascending"/>&#8593;
                                </option>
                                <option value="DESC"><fmt:message key="home_jsp.select.descending"/>&#8595;
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="ASC"><fmt:message key="home_jsp.select.ascending"/>&#8593;</option>
                                <option value="DESC" selected><fmt:message key="home_jsp.select.descending"/>&#8595;
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <hr>
                    <h4><fmt:message key="expos_jsp.search.price"/></h4>
                    <select class="custom-select" name="search_price">
                        <c:choose>
                            <c:when test="${priceOrder eq 'ASC'}">
                                <option value="ASC" selected><fmt:message
                                        key="home_jsp.select.ascending"/>&#8593;
                                </option>
                                <option value="DESC"><fmt:message key="home_jsp.select.descending"/>&#8595;
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="ASC"><fmt:message key="home_jsp.select.ascending"/>&#8593;</option>
                                <option value="DESC" selected><fmt:message key="home_jsp.select.descending"/>&#8595;
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
            </form>
        </div>
        <div class="col-md-9">
            <c:if test="${currentPage > maxPage}">
                <h2>No data. You can return to exposition list <a href="?page=1">here</a></h2>
            </c:if>

            <c:forEach var="exposition" items="${expositionList}">

                <h3 class="title">${exposition.topic}</h3>
                <p>${exposition.description}</p>
                <p class="text-muted">
                    <i class="bi bi-calendar-event"></i> <fmt:message
                        key="home_jsp.exposition.start"/>: ${exposition.startDateFormatted} |
                    <fmt:message key="home_jsp.exposition.end"/>: ${exposition.endDateFormatted}.<br>
                    <i class="bi bi-alarm"></i> <fmt:message
                        key="home_jsp.exposition.hours"/>: ${exposition.startWorkTime} - ${exposition.endWorkTime}.<br>
                    <i class="bi bi-credit-card"></i> <fmt:message key="home_jsp.exposition.price"/>:
                    &#8372;${exposition.price}
                </p>
                <ul class="list-inline">
                    <li class="list-inline-item">
                        <c:choose>
                            <c:when test="${empty accountRole}">
                                <form class="form-inline" action="/jsp/login">
                                    <button type="submit" class="btn btn-outline-primary"><fmt:message
                                            key="home_jsp.exposition.button.visit"/></button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-outline-primary" data-toggle="modal"
                                        data-target="#modal${exposition.id}"><fmt:message
                                        key="home_jsp.exposition.button.visit"/></button>
                            </c:otherwise>
                        </c:choose>
                        <!-- Modal -->
                        <div class="modal fade" id="modal${exposition.id}" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel"><fmt:message
                                                key="home_jsp.exposition.button.visit"/> ${exposition.topic}</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form name="visit" id="visit${exposition.id}" action="/jsp/visitExpo"
                                              method="post">
                                            <input type="hidden" name="exposition_id" value="${exposition.id}">
                                            <input type="hidden" name="account_id" value="${account.id}">
                                            <input type="hidden" name="exposition_price" value="${exposition.price}">
                                            <label for="quantity${exposition.id}"><fmt:message
                                                    key="home_jsp.exposition.quantity"/>? (1-10)</label>
                                            <input type="number" id="quantity${exposition.id}" name="quantity" min="1"
                                                   max="10"
                                                   oninput="calc(${exposition.id})" required>
                                            <p><fmt:message key="home_jsp.exposition.cost"/>: &#8372;<span
                                                    id="ticket_price${exposition.id}">${exposition.price}</span><br>
                                                <fmt:message key="home_jsp.exposition.total"/>: &#8372;<span
                                                        id="total${exposition.id}">0</span>
                                            </p>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" form="visit${exposition.id}" class="btn btn-primary">
                                            <fmt:message key="home_jsp.exposition.button.visit"/></button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <fmt:message key="home_jsp.exposition.button.close"/>
                                        </button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </li>
                    <c:if test="${accountRole == 'MODERATOR'}">
                        <li class="list-inline-item">
                            <form class="form-inline" action="/jsp/moderator/updateExpo">
                                <input type="hidden" name="exposition_id" value="${exposition.id}">
                                <button type="submit" class="btn btn-outline-secondary"><fmt:message
                                        key="home_jsp.exposition.button.edit"/></button>
                            </form>
                        </li>
                        <li class="list-inline-item">
                            <form class="form-inline" action="/jsp/moderator/deleteExpo" method="post">
                                <input type="hidden" name="exposition_id" value="${exposition.id}">
                                <button type="submit" class="btn btn-outline-danger"><fmt:message
                                        key="home_jsp.exposition.button.delete"/></button>
                            </form>
                        </li>
                        <li class="list-inline-item">
                            <form class="form-inline" action="/jsp/moderator/statsExpo" method="post">
                                <input type="hidden" name="exposition_id" value="${exposition.id}">
                                <button type="submit" class="btn btn-outline-dark"><fmt:message
                                        key="home_jsp.exposition.button.statistics"/></button>
                            </form>
                        </li>
                    </c:if>
                </ul>
                <hr>
            </c:forEach>
        </div>
    </div>
    <c:if test="${not empty expositionList}">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:choose>
                    <c:when test="${currentPage == 1}">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">Previous</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage-1}" tabindex="-1">Previous</a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <c:forEach begin="${(currentPage-3 <= 0) ? 1 : currentPage-2}"
                           end="${(currentPage-3 <= 0) ? (maxPage >= 5 ? 5 : maxPage) : (maxPage >= currentPage+2 ? currentPage+2 : maxPage)}"
                           var="val">
                    <c:choose>
                        <c:when test="${val == currentPage}">
                            <li class="page-item active"><a class="page-link" href="?page=${val}">${val}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="?page=${val}">${val}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${currentPage < maxPage}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage+1}">Next</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <a class="page-link" href="#">Next</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </c:if>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="/js/jquery-3.6.0.min.js" />
<script src="/js/bootstrap.min.js" />
<script>
    function calc(id) {
        var price = document.getElementById("ticket_price" + id).innerHTML;
        var noTickets = document.getElementById("quantity" + id).value;
        var total = parseFloat(price) * noTickets;
        if (!isNaN(total)) {
            document.getElementById("total" + id).innerHTML = total;
        }
    }
</script>
</body>
</html>