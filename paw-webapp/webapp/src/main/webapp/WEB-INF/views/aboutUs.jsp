<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>

    <title>About us</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/aboutUs.css" />" />


</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${4}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
<div class="about-section">
<div class="">
    <h1 class="about-us"><spring:message code="aboutUs.whatIsBandiy"/></h1>
    <span><spring:message code="aboutUs.bandifyTeam"/></span>
    <br>
    <span><spring:message code="aboutUs.bandifyAbout"/></span>
</div>
    <br>
    <hr>
<h2 style="text-align:center; margin-bottom: 2rem" class="about-us"><spring:message code="aboutUs.inBandify"/></h2>
    <div class="items">
        <div class="card">
           <h1><spring:message code="aboutUs.beArtist"/> </h1>
            <hr>
            <p> <spring:message code="aboutUs.asArtist"/> </p>
            <p> <spring:message code="aboutUs.artistLookingFor"/> </p>

                <ul>
                    <li> <spring:message code="aboutUs.completeArtistForm"/> </li>
                    <li><spring:message code="aboutUs.waitForContactArtist"/> </li>
                </ul>
            <br>

        </div>
        <div class="card">
            <h1><spring:message code="aboutUs.beBand"/></h1>
            <hr>
            <p><spring:message code="aboutUs.asBand"/> </p>
            <p><spring:message code="aboutUs.bandLookingFor"/> </p>

            <ul>
                <li> <spring:message  code="aboutUs.completeBandForm"/> </li>
                <li><spring:message code="aboutUs.mailingBand"/> </li>
            </ul>
            <br>
        </div>
    </div>
    <br>
    <div class="buttons ">
        <a href="<c:url value="/register"/>" class="purple-hover-button"><spring:message code="aboutUs.goToRegister"/> </a>
    </div>
</div>
</body>
</html>
