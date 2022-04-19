<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/forms.css" />"/>

    <style>
        .register-content {
            display: flex;
            justify-content: center;
            padding: 24px;
        }

        .box {
            background-color: #efefef;
            opacity: 0.9;
            width: 60%;
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

<div class="register-content flex flex-col">
    REGISTER
    <a href="<c:url value="/registerArtist"/>">ARTIST</a>
    <a href="<c:url value="/registerBand"/>">BAND</a>
</div>


</body>
</html>