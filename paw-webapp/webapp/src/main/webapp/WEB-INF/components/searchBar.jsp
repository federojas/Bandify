<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.searchBar"/> </title>

    <c:import url="../config/generalHead.jsp" />
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/searchBar.css" />" />
    <script>
        const queryString = window.location.search;
        let parameters = new URLSearchParams(queryString);
        let params = [parameters.getAll('order'), parameters.getAll('location'), parameters.getAll('genre'), parameters.getAll('role')]
        let i = 0;
        $(document).ready(function () {
            $(".select-wrapper").each(function () {
                var wrapper = this;
                let j = 0;
                $(this).find("ul>li").each(function () {
                    var li = this;
                    var option_text = $(this).text();
                    if (i > 0) {
                        if (params[i].includes(option_text)) {
                            $(li).click();
                        }
                    } else {
                        if (j === 0 && params[i][0] === "DESC") {
                            $(li).click();
                        }
                        if (j === 1 && params[i][0] == "ASC") {
                            $(li).click();
                        }
                        j+=1;
                    }
                });


                i+=1;
            });

            $(".search").click();
        });

        function search() {
            let list = [];
            let i = 0;
            $(".select-wrapper").each(function () {
                var wrapper = this;
                if (i > 0) {
                    $(this).find("ul>li").each(function () {
                        var li = this;
                        if ($(li).hasClass("active")) {
                            list.push($(li).text());
                        }

                    });
                }
                i+=1;
            });
            $('select').val(list).trigger('update');
        }

    </script>
</head>
<body>
<div class="search-general-div">
    <c:url value="/search" var="searchUrl"/>
    <spring:message code="search.placeholder" var="searchPlaceholder"/>
    <form action="${searchUrl}" method="get" class="searchForm">
        <div class="searchBarAndOrderBy">
            <div class="search">
                <input type="text" maxlength="80" size="43" placeholder="${searchPlaceholder}" name="query">
                <button type="submit" aria-hidden="true" onclick="search()"></button>
            </div>
            <div id="orderBy-filter" class="orderBy">
                <select name="order">
                    <option value="DESC" selected><spring:message code="filters.order.desc"/></option>
                    <option value="ASC"><spring:message code="filters.order.asc"/></option>
                </select>
            </div>
        </div>
        <div class="filters">
            <div class="filter-by">
                <b><p><spring:message code="filters.title"/></p></b>
            </div>
            <div>
                <select id="location-filter" multiple name="location">
                    <option disabled selected><spring:message code="filters.location"/></option>
                    <c:forEach var="location" items="${locationList}" varStatus="loop">
                        <option value="${location}"><c:out value="${location}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <select id="genre-filter" multiple name="genre">
                    <option disabled selected><spring:message code="filters.genres"/></option>
                    <c:forEach var="genre" items="${genreList}" varStatus="loop">
                        <option value="${genre}"><c:out value="${genre}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <select id="role-filter" multiple name="role">
                    <option disabled selected><spring:message code="filters.roles"/></option>
                    <c:forEach var="role" items="${roleList}" varStatus="loop">
                        <option value="${role}"><c:out value="${role}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </form>
</div>
</body>
</html>
