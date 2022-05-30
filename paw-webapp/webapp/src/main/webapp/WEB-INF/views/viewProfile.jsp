<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.viewprofile"/></title>
    <c:import url="../config/generalHead.jsp"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/profile.css" />"/>
</head>
<body>


<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${4}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
<main>
    <div class="bg-gray-100">
        <div class="main-box">
            <div class="md:flex no-wrap justify-center md:-mx-2 ">
                <!-- Left Side -->
                <div class="left-side view-public-profile">
                    <%--          ProfileCard      --%>
                    <div class="profile-card">
                        <%--                    Image--%>
                        <div class="image overflow-hidden">
                            <div class="profile-image-container">
                                <img class="profileImage"
                                <spring:message code="profile.img.alt" var="img"/>
                                     src="<c:url value="/user/${user.id}/profile-image"/>"
                                     alt="${img}">
                            </div>
                        </div>
                        <%--                    Info--%>
                        <div class="profile-left-info">
                            <h1 class="full-name">
                                <c:out value=" ${user.name}" />
                                <c:if test="${!user.band}">
                                    <c:out value=" ${user.surname}"/>
                                </c:if>
                            </h1>
                            <c:if test="${!user.band}">
                            <span
                                    class="account-type-label-artist"><spring:message code="register.artist_word"/> </span>
                            </c:if>
                            <c:if test="${user.band}">
                                <span
                                        class="account-type-label-band"><spring:message code="register.band_word"/> </span>
                            </c:if>

                        </div>
                            <c:if test="${user.band}">
                                <div class="auditions-div">
                                    <ul>
                                        <li class="pt-2">
                                            <a href="<c:url value="/bandAuditions/${user.id}"/>">
                                                <button class="auditions-btn hover: shadow-sm">
                                                    <spring:message code="viewprofile.bandAuditions"/>
                                                </button>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </c:if>
                    </div>
                </div>
                <!-- Right Side -->
                <div class="w-full md:w-6/12 mx-2 h-64">
                    <%--  About --%>
                    <div class="user-data">
                        <div class="about-section-heading">
                            <spring:message code="profile.user.alt" var="userimg"/>
                            <img src="<c:url value="/resources/icons/user.svg"/>" class="user-icon" alt="${userimg}"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<span><spring:message code="viewProfile.about"/></span>
                        </div>
                        <div>
                            <c:if test="${user.description==null || (user.description.length() == 0)}" >
                                <p><spring:message code="viewprofile.nobio" /></p>
                            </c:if>
                            <c:if test="${!(user.description==null)}" >
                                <p><c:out value="${user.description}"/></p>
                            </c:if>
                        </div>
                    </div>

                    <%--  Prefered genres                  --%>
                    <div class="user-data">
                        <div class="about-section-heading">
                        <span>
                            <c:if test="${user.band}">
                                <p><spring:message code="profile.bandGenres"/> </p>
                            </c:if>
                            <c:if test="${!user.band}">
                                <p><spring:message code="profile.userGenres"/> </p>
                            </c:if>
                        </span>
                        </div>
                        <div class="genres-div">
                            <c:if test="${preferredGenres.size() > 0}">
                                <c:forEach var="genre" items="${preferredGenres}">
                                    <span class="genre-span"><c:out value="${genre.name}" /></span>
                                </c:forEach>
                            </c:if>
                            <c:if test="${preferredGenres.size() == 0}">
                                <span><spring:message code="viewprofile.nogenres"/></span>
                            </c:if>
                        </div>
                    </div>

                    <%--  Roles                 --%>
                    <div class="user-data">
                        <div class="about-section-heading">
                        <span>
                            <c:if test="${user.band}">
                                <p><spring:message code="profile.bandRoles"/> </p>
                            </c:if>
                            <c:if test="${!user.band}">
                                <p><spring:message code="profile.userRoles"/> </p>
                            </c:if>
                        </span>
                        </div>
                        <div class="roles-div">
                            <c:if test="${roles.size() > 0}">
                                <c:forEach var="role" items="${roles}">
                                    <span class="roles-span"><c:out value="${role.name}" /></span>
                                </c:forEach>
                            </c:if>

                            <c:if test="${roles.size() == 0}">
                                <span><spring:message code="viewprofile.noroles"/></span>
                            </c:if>
                        </div>
                    </div>
                    <div class="user-data">
                        <div class="about-section-heading">
                            <span>

                                    <p><spring:message code="profile.socialMedia"/> </p>

                            </span>
                        </div>
                        <div class="roles-div">
                            <c:if test="${user.socialSocialMedia.size() == 0}">
                                <p><spring:message code="viewprofile.nosocialmedia"/></p>
                            </c:if>
                        </div>
                        <div class="social-media-container">
                            <c:forEach var="social" items="${user.socialSocialMedia}" varStatus="loop">
                                <a href="<c:url value="${social.url}" />">
                                    <img class="social-media-icons"
                                        <spring:message code="profile.${social.type.type}.alt" var="${social.type.type}"/>
                                         src="<c:url value="/resources/images/${social.type.type}.png"/>"
                                         alt="${social.type.type}">
                                </a>
                            </c:forEach>
                        </div>
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