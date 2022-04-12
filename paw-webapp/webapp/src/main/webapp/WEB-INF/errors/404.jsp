<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <link rel="stylesheet" href="public/styles/404.css" />
</head>
<body>
<div class="container">
    <img src="public/images/Error_1.png" alt="404image" />
    <p class="wrong-para"><spring:message code="404.errorMessage"/></p>
</div>
</body>
</html>
