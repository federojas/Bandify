<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>SearchBar</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/searchBar.css" />"/>
</head>
<body>
<div>
    <c:url value="/search" var="searchUrl"/>
    <spring:message code="search.placeholder" var="searchPlaceholder"/>
    <div class="search">
        <form action="${searchUrl}" method="get">
            <input type="text" class="searchTerm" placeholder="${searchPlaceholder}" name="query">

        </form>
    </div>
</div>
</body>
</html>
