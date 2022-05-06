<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
    <title><spring:message code="navbar.profile" /></title>
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
                            <sec:authorize access="hasRole('ARTIST')">
                                <c:out value=" ${user.surname}"/>
                            </sec:authorize>
                        </h1>
                        <sec:authorize access="hasRole('ARTIST')">
                            <span
                                    class="account-type-label-artist"><spring:message code="register.artist_word"/> </span>
                        </sec:authorize>
                        <sec:authorize access="hasRole('BAND')">
                                <span
                                        class="account-type-label-band"><spring:message code="register.band_word"/> </span>
                        </sec:authorize>

                        <c:out value="${user.email}"/>
                    </div>
                    <%--                        Edit button--%>
                    <div class="edit-div">
                        <a href="<c:url value="/profile/edit" />">
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
                    <%--                        MyAuditions (Band)--%>
                    <sec:authorize access="hasRole('BAND')">
                        <div class = "auditions-div">
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
                        <c:if test="${user.description==null}" >
                            <p><spring:message code="profile.emptyBiography"/> </p>
                        </c:if>
                        <c:if test="${!(user.description==null)}" >
                            <c:out value="${user.description}"/>
                        </c:if>
                    </div>
                </div>

<%--                &lt;%&ndash;  Experience                  &ndash;%&gt;--%>
<%--                <div class="user-data">--%>
<%--                    <div class="about-section-heading"><span><spring:message code="profile.experience"/></span></div>--%>

<%--                </div>--%>

                <%--  Prefered genres                  --%>
                <div class="user-data">
                    <div class="about-section-heading"><span><spring:message code="profile.preferedGenres"/></span></div>
                    <div class="genres-div">
                        <c:forEach var="genre" items="${preferredGenres}">
                            <span class="genre-span"><c:out value="${genre.name}" /></span>
                        </c:forEach>
                    </div>
                </div>

                <%--  Roles                 --%>
                <div class="user-data">
                    <div class="about-section-heading"><span><spring:message code="profile.roles"/></span></div>
                    <div class="roles-div">
                        <c:forEach var="role" items="${roles}">
                            <span class="roles-span"><c:out value="${role.name}" /></span>
                        </c:forEach>
                    </div>
                </div>
                <%--  MyApplications --%>
                <sec:authorize access="hasRole('ARTIST')">
                    <div class="user-data">

                        <div class="myApplicationsHeader">
                            <spring:message code="profile.myApplications"/>
                        </div>
                        <c:forEach var="artistApplication" items="${artistApplications}">
                            <jsp:include page="../components/artistApplicationItem.jsp">
                                <jsp:param name="artistApplicationState" value="${artistApplication.state}"/>
                                <jsp:param name="auditionTitle" value="${artistApplication.auditionTitle}"/>
                                <jsp:param name="auditionId" value="${artistApplication.auditionId}"/>
                            </jsp:include>
                        </c:forEach>


                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>
</div>
</body>
</html>