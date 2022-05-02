<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>403</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/404.css" />" />
</head>
<body>
<div class="container">
    <spring:message code="img.alt.403" var="img403"/>
    <img src="<c:url value="/resources/images/403.png" />" alt="${img403}" style="margin-bottom: 2rem" />
    <p class="wrong-para"><spring:message code="403.errorMessage"/></p>
</div>
</body>
</html>
