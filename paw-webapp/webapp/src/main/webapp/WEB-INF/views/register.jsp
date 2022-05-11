<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<head>
    <title><spring:message code="title.register"/></title>
    <c:import url="../config/generalHead.jsp"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/register.css" />"/>
    <script src="<c:url value="/resources/js/register.js" />"></script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<main>
<div class="register-content flex flex-col">
    <div class="card-content">
        <div class="header">
            <spring:message code="register.header"/>
            <div class="forms-buttons">
                <c:choose>
                    <c:when test="${isBand}">
                        <button id="artist-button" onclick="toggleForm(false)"
                                style="background-color: rgba(108, 12, 132, 0.69);">
                            <spring:message code="register.artist_word"/>
                        </button>
                        <button id="band-button" onclick="toggleForm(true)">
                            <spring:message code="register.band_word"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button id="artist-button" onclick="toggleForm(false)">
                            <spring:message code="register.artist_word"/>
                        </button>
                        <button id="band-button" onclick="toggleForm(true)"
                                style="background-color: rgba(108, 12, 132, 0.69);">
                            <spring:message code="register.band_word"/>
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${!isBand}">
                <div id="artist-form" style="display: block;">
                    <jsp:include page="../components/registerArtistForm.jsp">
                        <jsp:param name="artist" value="${1}"/>
                    </jsp:include>
                </div>
                <div id="band-form" style="display: none; ">
                    <jsp:include page="../components/registerBandForm.jsp">
                        <jsp:param name="band" value="${1}"/>
                    </jsp:include>
                </div>
            </c:if>
            <c:if test="${isBand}">
                <div id="artist-form" style="display: none;">
                    <jsp:include page="../components/registerArtistForm.jsp">
                        <jsp:param name="artist" value="${1}"/>
                    </jsp:include>
                </div>
                <div id="band-form" style="display: block; ">
                    <jsp:include page="../components/registerBandForm.jsp">
                        <jsp:param name="band" value="${1}"/>
                    </jsp:include>
                </div>
            </c:if>
        </div>
    </div>
    <div id="snackbar"><spring:message code="snackbar.message"/></div>
</div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>