<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />"/>
    <script type="text/javascript" src="/resources/js/alerts.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <script src="<c:url value="/resources/js/login.js" />"></script>

</head>

<body>

<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

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

        <%--        Log in --%>
        <div class="login-box">
            <div id="login" style="display: block;">
                <form action="<c:url value="/welcome"/> " method="post">
                    <div class="form-group">
                        <label for="email" class="form-label">
                            <spring:message code="welcome.email"/>
                        </label>
                        <input type="text" required class="form-input" id="email" name="email"
                               placeholder="<spring:message code="welcome.email"/>"/>
                        <p id="invalidMail" class="error" style="display: none"><spring:message code="Welcome.error.invalidMail"/> </p>

                    </div>
                    <div class="form-group">
                        <label for="password" class="form-label">
                            <spring:message code="welcome.password"/>
                        </label>
                        <input type="password" required class="form-input" id="password" name="password"
                               placeholder="<spring:message code="welcome.password"/>"/>
                    </div>
                    <p id="invalidPassword" class="error" style="display: none"><spring:message code="Welcome.error.invalidPassword"/> </p>

                    <div>
                        <input type="checkbox" id="rememberme" class="remember-me" value="false"/>
                        <spring:message code="welcome.rememberme"/>
                    </div>
                    <div class="end-button-div">
                        <button type="submit" onclick="return loginFormCheck()" class="purple-hover-button">
                            <spring:message code="welcome.loginButton"/>
                        </button>
                    </div>
                </form>
                <p><spring:message code="welcome.notMemberYet"/></p>
                <a href="<c:url value="/register"/>"><u style="cursor: pointer;"><spring:message code="welcome.registerButton"/></u></a>
        </div>
    </div>
</div>
<div id="snackbar"><spring:message code="snackbar.message"/></div>

</body>
</html>
