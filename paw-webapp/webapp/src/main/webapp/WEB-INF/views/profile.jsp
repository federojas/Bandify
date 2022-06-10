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
            <div class="user-info-div">
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
                                <c:if test="${user.available}">
                                    <spring:message code="available.img.alt" var="available"/>
                                    <img class="top-image-big" src="<c:url value="/resources/images/available.png"/>" alt="${available}"/>
                                </c:if>
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
                            <div class="location">
                                <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        viewBox="0 0 24 24"
                                        width="14"
                                        height="25"
                                        class="locationImg"
                                >
                                    <path d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z"/>
                                </svg>
                                <c:if test="${not empty location}">
                                    <p><c:out value="${location.name}" /></p>
                                </c:if>
                                <c:if test="${empty location}">
                                    <p><spring:message code="profile.emptyLocation"/></p>
                                </c:if>
                            </div>
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
                                <p class="description"><c:out value="${user.description}"/></p>
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
            <c:if test="${user.band}">
                <div class="member-data">
                    <div class="top">
                        <div class="about-section-heading">
                            <spring:message code="profile.members.alt" var="memberalt"/>
                            <img src="<c:url value="/resources/icons/members.svg"/>" class="members-icon" alt="${memberalt}"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<span><spring:message code="profile.members"/></span>
                        </div>
                        <c:if test="${members.size() != 0}">
                            <div class="view-button-div">
                                <a href="<c:url value="/profile/bandMembers" />">
                                    <button class="edit-btn hover: shadow-sm">
                                        <spring:message code="profile.edit.alt" var="edit"/>
                                        <img src="<c:url value="/resources/icons/edit-white-icon.svg"/>"
                                             alt="${edit}"
                                             class="icon-img"
                                        />
                                        <spring:message code="profile.edit"/>
                                    </button>
                                </a>
                            </div>
                        </c:if>
                    </div>
                        <div class="members-data">
                            <c:if test="${members.size() > 0}">
                                <div class="members-div">
                                    <c:forEach var="member" items="${members}" varStatus="loop">
                                        <c:set
                                                var="memberRoles"
                                                value="${member.roles}"
                                                scope="request"
                                        />
                                        <jsp:include page="../components/memberItem.jsp">
                                            <jsp:param name="memberName" value="${member.artist.name}" />
                                            <jsp:param name="memberSurname" value="${member.artist.surname}" />
                                            <jsp:param name="userId" value="${member.artist.id}" />
                                            <jsp:param name="description" value="${member.description}"/>
                                            <jsp:param name="available" value="${member.artist.available}" />
                                            <jsp:param name="isBand" value="false" />
                                        </jsp:include>
                                        <c:if test="${loop.count < members.size()}">
                                            <div class="vertical-line"></div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:if>
                            <c:if test="${members.size() == 0}">
                                <p class="no-members">
                                    <spring:message code="profile.noMembers.private"/>
                                </p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:if>
        <c:if test="${!user.band}">
        <div class="play-data">
            <div class="top">
                <div class="about-section-heading">
                    <spring:message code="profile.members.alt" var="memberalt"/>
                    <img src="<c:url value="/resources/icons/members.svg"/>" class="members-icon" alt="${memberalt}"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;<span><spring:message code="profile.plays.in"/></span>
                </div>
                <c:if test="${members.size() != 0}">
                    <div class="view-button-div">
                        <a href="<c:url value="/profile/bands" />">
                            <button class="view-all-btn hover: shadow-sm">
                                <spring:message code="profile.plays.viewAll"/>
                            </button>
                        </a>
                    </div>
                </c:if>
            </div>
            <div class="members-data">
                <c:if test="${members.size() > 0}">
                    <div class="members-div">
                        <c:forEach var="member" items="${members}" varStatus="loop">
                            <c:set
                                    var="memberRoles"
                                    value="${member.roles}"
                                    scope="request"
                            />
                            <jsp:include page="../components/memberItem.jsp">
                                <jsp:param name="memberName" value="${member.band.name}" />
                                <jsp:param name="memberSurname" value="" />
                                <jsp:param name="userId" value="${member.band.id}" />
                                <jsp:param name="description" value="${member.description}"/>
                                <jsp:param name="available" value="false" />
                                <jsp:param name="isBand" value="true" />
                            </jsp:include>
                            <c:if test="${loop.count < members.size()}">
                                <div class="vertical-line"></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${members.size() == 0}">
                    <p class="no-members">
                        <spring:message code="profile.noPlays.private"/>
                    </p>
                </c:if>
            </div>
        </div>
    </div>
    </c:if>
        </div>
    </div>
</main>
</body>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</html>