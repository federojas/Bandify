<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.profile"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
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
                                <c:out value=" ${user.name}"/>
                                <sec:authorize access="hasRole('ARTIST')">
                                    <c:out value=" ${user.surname}"/>
                                </sec:authorize>
                            </h1>
                            <sec:authorize access="hasRole('ARTIST')">
                            <span
                                    class="account-type-label-artist"><spring:message
                                    code="register.artist_word"/> </span>
                            </sec:authorize>
                            <sec:authorize access="hasRole('BAND')">
                                <span
                                        class="account-type-label-band"><spring:message
                                        code="register.band_word"/> </span>
                            </sec:authorize>

                            <h1 class="email">
                                <c:out value="${user.email}"/>
                            </h1>
                        </div>
                        <%--                        Edit button--%>
                        <sec:authorize access="hasRole('ARTIST')">
                            <div class="edit-div">

                                <a href="<c:url value="/profile/editArtist" />">
                                    <button class="edit-btn hover: shadow-sm">
                                        <spring:message code="profile.edit.alt" var="edit"/>

                                        <img src="<c:url value="/resources/icons/edit-white-icon.svg"/>"
                                             alt="${edit}"
                                             class="icon-img"
                                        />
                                        <spring:message code="profile.editProfile"/>
                                    </button>
                                </a>

                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('BAND')">
                            <div class="edit-div">

                                <a href="<c:url value="/profile/editBand" />">
                                    <button class="edit-btn hover: shadow-sm">
                                        <spring:message code="profile.edit.alt" var="edit"/>

                                        <img src="<c:url value="/resources/icons/edit-white-icon.svg"/>"
                                             alt="${edit}"
                                             class="icon-img"
                                        />
                                        <spring:message code="profile.editProfile"/>
                                    </button>
                                </a>
                            </div>
                        </sec:authorize>
                        <%--                        MyAuditions (Band)--%>
                        <sec:authorize access="hasRole('BAND')">
                            <div class="auditions-div">
                                <ul>
                                    <li class="pt-2">
                                        <a href="<c:url value="/profile/auditions"/>">
                                            <button class="auditions-btn hover: shadow-sm">
                                                <spring:message code="profile.auditions"/>
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ARTIST')">
                            <div class="auditions-div">
                                <ul>
                                    <li class="pt-2">
                                        <a href="<c:url value="/profile/applications"/>">
                                            <button class="auditions-btn hover: shadow-sm">
                                                <spring:message code="profile.myApplications"/>
                                            </button>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </sec:authorize>
                    </div>
                </div>
                <!-- Right Side -->
                <div class="w-full md:w-6/12 mx-2 h-64">
                    <%--  About --%>
                    <div class="user-data">
                        <div class="about-section-heading">
                            <spring:message code="profile.user.alt" var="userimg"/>
                            <img src="<c:url value="/resources/icons/user.svg"/>" class="user-icon" alt="${userimg}"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<span><spring:message code="profile.about"/></span>
                        </div>
                        <div>
                            <c:if test="${user.description==null}">
                                <c:if test="${user.band}">
                                    <p><spring:message code="profile.bandEmptyBiography"/></p>
                                </c:if>
                                <c:if test="${!user.band}">
                                    <p><spring:message code="profile.userEmptyBiography"/></p>
                                </c:if>
                            </c:if>
                            <c:if test="${!(user.description==null)}">
                                <c:if test="${(user.description.length() == 0)}">
                                    <c:if test="${user.band}">
                                        <p><spring:message code="profile.bandEmptyBiography"/></p>
                                    </c:if>
                                    <c:if test="${!user.band}">
                                        <p><spring:message code="profile.userEmptyBiography"/></p>
                                    </c:if>
                                </c:if>
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
                            <c:if test="${preferredGenres.size() == 0}">
                                <c:if test="${user.band}">
                                    <p><spring:message code="profile.band.noGenres"/></p>
                                </c:if>
                                <c:if test="${!user.band}">
                                    <p><spring:message code="profile.artist.noGenres"/></p>
                                </c:if>

                            </c:if>
                            <c:if test="${preferredGenres.size() > 0}">
                                <c:forEach var="genre" items="${preferredGenres}">
                                    <span class="genre-span"><c:out value="${genre.name}"/></span>
                                </c:forEach>
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
                            <c:if test="${roles.size() == 0}">
                                <c:if test="${user.band}">
                                    <p><spring:message code="profile.band.noRoles"/></p>
                                </c:if>
                                <c:if test="${!user.band}">
                                    <p><spring:message code="profile.artist.noRoles"/></p>
                                </c:if>

                            </c:if>
                            <c:if test="${roles.size() > 0}">
                                <c:forEach var="role" items="${roles}">
                                    <span class="roles-span"><c:out value="${role.name}"/></span>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <%--social networks    --%>
                    <div class="user-data">
                        <div class="about-section-heading">
                    <span>

                            <p><spring:message code="profile.socialMedia"/> </p>

                    </span>
                        </div>
                        <div class="roles-div">
                            <c:if test="${socialMedia.size() == 0}">
                                    <p><spring:message code="profile.noSocialMedia"/></p>
                            </c:if>
                        </div>
                        <div class="social-media-container">
                            <c:forEach var="social" items="${socialMedia}" varStatus="loop">
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