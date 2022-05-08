<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title><spring:message code="title.resetemailsent"/></title>
    <c:import url="../config/generalHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/success.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/emailSent.js" />"></script>
</head>
<body onload="load()">
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<div class="success-content">
    <spring:message code="email_sent.email" var="email_alt"/>
    <img src="<c:url value="/resources/icons/mail.svg"/>" class="success-icon" alt="${email_alt}"/>
    <h1><spring:message code="email_sent.title"/></h1>
    <p><spring:message code="email_sent.p0"/><b><c:out value="${email}" /></b></p>
    <p><spring:message code="email_sent.reset"/></p>
    <p><spring:message code="email_sent.didntrecieve" /></p>
    <c:url value="/resetEmailSent" var="resendEmailUrl" />

    <form method="post" action="${resendEmailUrl}">
        <input type="hidden" name="email" value="${email}"/>
        <button type="submit" class="resend-button" id="resendButton" disabled = true>
            <spring:message code="email_sent.resend" /></button>
    </form>

    <a class="back-bandify" href="<c:url value="/welcome" />">
        <spring:message code="email_sent.link"/>
    </a>
</div>


</body>
</html>
