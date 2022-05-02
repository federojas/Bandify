<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>SearchBar</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/searchBar.css" />"/>
    <script src="<c:url value="/resources/js/sideNav.js" />"></script>

</head>
<body>
<div>
    <c:url value="/search" var="searchUrl"/>
    <spring:message code="search.placeholder" var="searchPlaceholder"/>
    <div style="display: flex">
    <form action="${searchUrl}" method="get" class="searchForm">
        <div class="search">
            <input type="text" maxlength="80" size="43" placeholder="${searchPlaceholder}" name="query">
            <button type="submit" aria-hidden="true"></button>
        </div>
    </form>
    <jsp:include page="../components/sideNav.jsp">
        <jsp:param name="genreList" value="${genreList}" />
        <jsp:param name="role" value="${roleList}" />
        <jsp:param name="location" value="${locationList}"/>

    </jsp:include>
    </div>
</div>
</body>
</html>
