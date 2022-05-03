<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>401</title>
</head>
<body>
<spring:message code="img.alt.401" var="img401"/>
<img src="<c:url value="/resources/images/401.png" />" alt="${img401}" style="margin-bottom: 2rem" />
<p class="wrong-para"><spring:message code="401.errorMessage"/></p>
</body>
</html>
