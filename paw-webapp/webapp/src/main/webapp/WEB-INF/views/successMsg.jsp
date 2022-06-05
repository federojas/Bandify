<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="title.successmsg"/></title>
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

<main>
    <div class="success-content">
        <spring:message code="success.success.alt" var="success"/>
        <img src="<c:url value="/resources/icons/success.svg"/>" class="success-icon" alt="${success}"/>
        <h1><spring:message code="success.title"/></h1>
        <p><spring:message code="success.p"/></p>
        <a class="back-bandify" href="<c:url value="/auditions" />">
            <spring:message code="success.link"/>
        </a>
    </div>

</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>