<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/forms.css" />"/>
    <style>
        body {
            /* gray-100 */
            --tw-bg-opacity: 1;
            background-color: rgb(243 244 246 / var(--tw-bg-opacity));
        }

        .content {
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
        }

        .guitar-hero {
            position: relative;
        }

        .hero-title {
            position: absolute;
            top: 10%;
            left: 5%;
            color: #efefef;
            width: 50%;
        }

        .hero-title > span {
            font-weight: 700;
            font-size: 2.5rem;
            line-height: 2.75rem;
        }

        .hero-title > p {
            font-size: 1.5rem /* 18px */;
            line-height: 1.75rem /* 28px */;
            margin-top: 1rem;
        }

        .hero-buttons {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            margin-top: 3rem;
            font-size: 1.5rem /* 18px */;
            line-height: 1.75rem /* 28px */;
        }

        .buttons {
            display: flex;
            flex-direction: row;
            justify-content: flex-start;
        }

        .purple-hover-button {
            padding: 0.5rem 1.25rem;
            color: #ffffff;
            font-weight: 600;
            border-radius: 9999px;
            background-color: #1c041c !important;
            margin: 1rem;
        }

        .purple-hover-button:nth-child(1n) {
            margin-left: 0;
        }

        .purple-hover-button:nth-child(2n) {
            margin-right: 0;
        }

        .purple-hover-button:hover {
            border-width: 2px;
            border-color: #ffffff;
        }

        .login-box {
            position: absolute;
            top: 10%;
            right: 10%;
            background-color: #efefef;
            opacity: 0.9;
            width: fit-content;
            padding: 0.5rem 1.5rem;
            border-color: #6c0c8436;
            border-radius: 0.75rem;
            border-width: 1px;
            border-style: dotted;
            font-size: 1.25rem;
            line-height: 1.5rem;
            font-weight: 600;
        }

    </style>
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
                    <div class="form-group">
                        <label for="password" class="form-label">
                            <spring:message code="welcome.password"/>
                        </label>
                        <input type="password" class="form-input" id="password" name="password"
                               placeholder="<spring:message code="welcome.password"/>"/>
                    </div>
                    <div class="end-button-div">
                        <button type="submit" class="purple-hover-button">
                            <spring:message code="welcome.loginButton"/>
                        </button>
                    </div>
                </form>
                <p><spring:message code="welcome.notMemberYet"/></p>
                <a href="<c:url value="/register"/>"><u style="cursor: pointer;"><spring:message code="welcome.registerButton"/></u></a>

            </div>
        </div>
    </div>
</div>
</body>
</html>
