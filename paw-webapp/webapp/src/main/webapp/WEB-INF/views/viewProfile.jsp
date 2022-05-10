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
<div class="bg-gray-100">
    <div class="main-box">
        <div class="md:flex no-wrap justify-center md:-mx-2 ">
            <!-- Left Side -->
            <div class="left-side">
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
                        <c:if test="${user.description==null}" >
                        </c:if>
                        <c:if test="${!(user.description==null)}" >
                            <c:out value="${user.description}"/>
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
            </div>
        </div>
    </div>
</div>
</body>
</html>