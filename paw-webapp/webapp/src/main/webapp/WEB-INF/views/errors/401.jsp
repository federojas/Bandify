<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.401"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/errorPage.css" />" />
</head>
<body>
<spring:message code="img.alt.401" var="img401"/>
<img src="<c:url value="/resources/images/401.png" />" alt="${img401}" class="image"/>
<p class="wrong-para"><spring:message code="401.errorMessage"/></p>
<div class="back-button-container">
    <a href="<c:url value="/"/>"><button class="error-back-button"><spring:message code="error.backButton"/> </button></a>
</div>
</body>
</html>
