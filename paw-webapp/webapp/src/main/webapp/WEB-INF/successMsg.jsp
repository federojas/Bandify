<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="icon" type="image/png" href="public/images/logo.png"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Questrial">
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><spring:message code="home.title"/></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css"/>
    <style>
        .success-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 2rem 1rem;
        }

        .success-content > h1 {
            font-size: 3rem;
            line-height: 1;
            font-weight: 700;
            padding: 1rem;
        }
        .success-content > p{
            font-size: 1.5rem;
            line-height: 1;
            font-weight: 500;
            padding: 1rem;
        }
        .success-content > a {
            font-size: 2rem;
            line-height: 1;
            font-weight: 500;
            padding: 1rem;
            margin: 6rem 0;
            text-decoration: underline;
        }
        .success-icon {
            width: 100px;
            height: 100px;
            margin: 1rem;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <jsp:include page="navbar.jsp">
        <jsp:param name="navItem" value="${1}"/>
        <jsp:param name="name" value="Bandify"/>
    </jsp:include>

    <div class="success-content">
        <img src="<c:url value="public/icons/success.svg"/>" class="success-icon" alt="success"/>
        <h1><spring:message code="success.title"/></h1>
        <p><spring:message code="success.p"/></p>
        <a href="<c:url value="/auditions" />">
            <spring:message code="success.link"/>
        </a>
    </div>


</body>
</html>
