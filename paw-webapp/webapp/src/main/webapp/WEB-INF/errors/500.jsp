<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/404.css" />" />
</head>
<body>
<div class="container">
    <spring:message code="img.alt.500" var="img500"/>
    <img src="<c:url value="/resources/images/500.png" />" alt="${img500}" style="margin-bottom: 2rem" />
    <p class="wrong-para"><spring:message code="404.errorMessage"/></p>
</div>
</body>
</html>
