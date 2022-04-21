<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/alerts.css" />"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formchecks.js"></script>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/forms.css" />"/>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/welcome.css" />"/>

    <!-- include jQuery  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>

    <!-- jQuery Modal -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
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
        <img src="<c:url value="${pageContext.request.contextPath}/resources/images/guitar.png"/>" alt="${guitar}"/>
        <%-- Hero text   --%>
        <div class="hero-title">
            <span><spring:message code="welcome.slogan1"/></span>
            <p><spring:message code="welcome.slogan2"/></p>
            <div class="hero-buttons">
                <p><spring:message code="welcome.options"/></p>
                <div class="buttons">
                    <a href="<c:url value="/auditions"/> ">
                        <button
                                type="button"
                                class="purple-hover-button "
                        >
                            <spring:message code="welcome.searchingBandsButton"/>
                        </button>
                    </a>
                    <a href="<c:url value="/newAudition"/> ">
                        <button
                                type="button"
                                class="purple-hover-button"
                        >
                            <spring:message code="welcome.searchingArtistsButton"/>
                        </button>
                    </a>
                </div>
            </div>
        </div>

        <%--        Log in --%>
        <div class="login-box">
            <div id="login" style="display: block;">
                <form action="<c:url value="/welcome"/> " method="post">
                    <div class="form-group">
                        <label for="email" class="form-label">
                            <spring:message code="welcome.email"/>
                        </label>
                        <input type="text" class="form-input" id="email" name="email"
                               placeholder="<spring:message code="welcome.email"/>"/>
                    </div>
                    <p id="wrongEmail" class="wrongMessage"><spring:message code="wrong.email"/></p>
                    <div class="form-group">
                        <label for="password" class="form-label">
                            <spring:message code="welcome.password"/>
                        </label>
                        <input type="password" class="form-input" id="password" name="password"
                               placeholder="<spring:message code="welcome.password"/>"/>
                    </div>
                    <p id="wrongPassword" class="wrongMessage"><spring:message code="wrong.password"/></p>

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

                <a rel="modal:open" href="#ex2"/><u style="cursor: pointer;"><spring:message code="welcome.registerButton"/></u></a>
                    <div id="ex2" class="modal center">
                        <h1><spring:message code="welcome.chooseRegister"/></h1>
                        <hr>
                        <div class="register-buttons">

                            <a class="purple-hover-button" href="<c:url value="/registerBand"/>"><spring:message code="register.band_word"/></a>
                        </div>
                        <div class="register-buttons">
                            <a class="purple-hover-button " href="<c:url value="/registerArtist"/>">Artista</a>
                        </div>
                    </div>
        </div>
    </div>
</div>
<div id="snackbar"><spring:message code="snackbar.message"/></div>

</body>
</html>
