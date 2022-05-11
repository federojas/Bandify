<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.500"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/errorPage.css" />" />
</head>
<body>
<div class="container">
    <spring:message code="img.alt.500" var="img500"/>
    <img src="<c:url value="/resources/images/500.png" />" alt="${img500}" class="image"/>
    <p class="wrong-para"><spring:message code="500.errorMessage"/></p>
</div>
</body>
</html>
