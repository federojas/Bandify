<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <c:import url="../config/generalHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/success.css" />"/>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<div class="success-content">
    <spring:message code="verified.alt" var="success"/>
    <img src="<c:url value="/resources/icons/success.svg"/>" class="success-icon" alt="${success}"/>
    <h1><spring:message code="verified.title"/></h1>
    <p><spring:message code="verified.p"/></p>
</div>


</body>
</html>