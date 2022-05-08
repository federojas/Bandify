<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="title.applicationfailed"/></title>
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
    <spring:message code="applicationFailed.alt" var="fail"/>
    <img src="<c:url value="/resources/icons/reject.svg"/>" class="success-icon" alt="${fail}"/>
    <h1><spring:message code="applicationFailed.title"/></h1>
    <p><spring:message code="applicationFailed.p"/></p>
    <a class="back-bandify" href="<c:url value="/auditions" />">
        <spring:message code="success.link"/>
    </a>
</div>


</body>
</html>