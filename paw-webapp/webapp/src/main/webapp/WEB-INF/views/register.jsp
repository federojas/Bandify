<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/register.css" />" />
    <script src="<c:url value="/resources/js/register.js" />"></script>

</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<div class="register-content flex flex-col">

    <div class="header">
        <spring:message code="register.header" />
        <div class="forms-buttons">
            <button id="artist-button" onclick="toggleForm()">
                <spring:message code="register.artist_word" />
            </button>
            <button id="band-button" onclick="toggleForm()" style="background-color: rgba(108, 12, 132, 0.69);">
                <spring:message code="register.band_word" />
            </button>
        </div>

    </div>

    <c:if test="${!isBand}">
        <div id="artist-form" style="display: block;">
            <jsp:include page="../components/registerArtistForm.jsp">
                <jsp:param name="artist" value="${1}" />
            </jsp:include>
        </div>
        <div id="band-form" style="display: none; ">
            <jsp:include page="../components/registerBandForm.jsp">
                <jsp:param name="band" value="${1}" />
            </jsp:include>
        </div>
    </c:if>
    <c:if test="${isBand}">
        <div id="artist-form" style="display: none;">
            <jsp:include page="../components/registerArtistForm.jsp">
                <jsp:param name="artist" value="${1}" />
            </jsp:include>
        </div>
        <div id="band-form" style="display: block; ">
            <jsp:include page="../components/registerBandForm.jsp">
                <jsp:param name="band" value="${1}" />
            </jsp:include>
        </div>
    </c:if>

</div>
<div id="snackbar"><spring:message code="snackbar.message"/></div>


</body>
</html>