<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.welcome"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/alerts.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
</head>

<body class="bg">

<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${0}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<main>
    <!-- Content -->
    <div class="content">
        <!-- Hero -->
        <div class="guitar-hero">
            <spring:message code="img.alt.guitar" var="guitar"/>
            <img src="<c:url value="/resources/images/guitar.png"/>" alt="${guitar}"/>
            <%-- Hero text   --%>
            <div class="hero-title">
                <span><spring:message code="welcome.slogan1"/></span>
                <p><spring:message code="welcome.slogan2"/></p>
            </div>

            <div class="welcome-loginform">
                <jsp:include page="../components/loginForm.jsp" />
            </div>

        </div>
    </div>
    <div id="snackbar"><spring:message code="snackbar.message"/></div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>
