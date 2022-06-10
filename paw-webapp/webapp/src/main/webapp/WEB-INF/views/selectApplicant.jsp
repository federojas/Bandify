<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page
        contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.applicants"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicants.css" />"/>
    <script src="<c:url value="/resources/js/pagination.js" />"></script>
    <script src="<c:url value="/resources/js/applicants.js"/>"></script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${2}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<main>
    <!-- Auditions content -->
    <div class="applicants-container">
        <div class="left-panel-abs">
            <a class="back-anchor" onclick="window.history.back();" style="cursor: pointer;">
                <div class="back-div">
                    <spring:message code="audition.alt.back" var="backAlt"/>
                    <img src="<c:url value="/resources/icons/back.svg" />" alt="${backAlt}" class="back-icon"/>
                </div>
            </a>
        </div>
        <div class="applicants-content">
            <h2 class="applicants-title">
                <spring:message code="applicants.title" arguments="${auditionTitle}"/>
            </h2>

            <div class="confirmation-add-to-band">
                <span class="welcome-title"><spring:message code="applicants.confirmation.title"/>
                </span>

                <hr class="rounded">

                <div class="confirmation-container">
                    <div class="header-welcome-artist">
                        <span class="welcome-title-2">
                        <spring:message code="applicants.confirmation.artist1"/>

                        </span>
                        <div class="image-and-name">
                            <spring:message code="profile.img.alt" var="img"/>
                            <img class="postcard-profile-image"
                                 src="<c:url value="/user/${applicantId}/profile-image"/>"
                                 alt="${img}"/>
                            <span class="user-name">
                                <c:out value="${applicantName}"/> <c:out value="${applicantSurname}"/>
                            </span>
                        </div>
                        <span class="welcome-title-2">

                        <spring:message code="applicants.confirmation.artist2"/>
                        </span>
                    </div>
                </div>

            </div>


        </div>

    </div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>