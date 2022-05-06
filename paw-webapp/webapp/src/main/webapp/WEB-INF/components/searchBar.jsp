<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>SearchBar</title>

    <c:import url="../config/generalHead.jsp" />
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/searchBar.css" />" />

</head>
<body>
<div class="search-general-div">
    <c:url value="/search" var="searchUrl"/>
    <spring:message code="search.placeholder" var="searchPlaceholder"/>
    <form action="${searchUrl}" method="get" class="searchForm">
        <div class="searchBarAndOrderBy">
            <div class="search">
                <input type="text" maxlength="80" size="43" placeholder="${searchPlaceholder}" name="query">
                <button type="submit" aria-hidden="true"></button>
            </div>
            <div class="orderBy">
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
                <select  multiple name="location">
                    <option disabled selected><spring:message code="filters.location"/></option>
                    <c:forEach var="location" items="${requestScope.locationList}" varStatus="loop">
                        <option value="${location}">${location}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <select  multiple name="genre">
                    <option disabled selected><spring:message code="filters.genres"/></option>
                    <c:forEach var="genre" items="${requestScope.genreList}" varStatus="loop">
                        <option value="${genre}">${genre}</option>
                    </c:forEach>
            </select>
            </div>
            <div>
                <select  multiple name="role">
                    <option disabled selected><spring:message code="filters.roles"/></option>
                    <c:forEach var="role" items="${requestScope.roleList}" varStatus="loop">
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </form>
</div>
</body>
</html>
