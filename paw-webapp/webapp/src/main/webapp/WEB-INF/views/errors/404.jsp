<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.404"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/404.css" />" />
</head>
<body>
<div class="container">
    <spring:message code="img.alt.404" var="img404"/>
    <img src="<c:url value="/resources/images/404.png" />" alt="${img404}" style="margin-bottom: 2rem" />
    <p class="wrong-para"><spring:message code="404.errorMessage"/></p>
</div>
</body>
</html>
