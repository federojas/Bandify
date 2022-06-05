<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title><spring:message code="title.405"/></title>
  <link rel="stylesheet" href="<c:url value="/resources/css/errorPage.css" />" />
</head>
<body>
<div class="container">
  <spring:message code="img.alt.405" var="img405"/>
  <img src="<c:url value="/resources/images/405.png" />" alt="${img405}" class="image"/>
  <p class="wrong-para"><spring:message code="405.errorMessage"/></p>
</div>
<div class="back-button-container">
  <a href="<c:url value="/"/>"><button class="error-back-button"><spring:message code="error.backButton"/> </button></a>
</div>
</body>
</html>
