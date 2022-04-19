<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/404.css" />" />
</head>
<body>
<div class="container">
    <spring:message code="img.alt.404" var="img404"/>
    <img src="<c:url value="${pageContext.request.contextPath}/resources/images/Error_1.png" />" alt="${img404}" />
    <p class="wrong-para"><spring:message code="404.errorMessage"/></p>
</div>
</body>
</html>
