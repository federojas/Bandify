<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.login"/></title>
    <c:import url="../config/generalHead.jsp"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/alerts.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <script src="<c:url value="/resources/js/login.js" />"></script>

</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${5}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<main class="flex justify-center">
    <div class="login-loginform">
        <jsp:include page="../components/loginForm.jsp" />
    </div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>
