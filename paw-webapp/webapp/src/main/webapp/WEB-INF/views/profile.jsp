<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
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
        <div class="md:flex no-wrap md:-mx-2 ">
            <!-- Left Side -->
            <div class="w-full md:w-3/12 md:mx-2">
                <!-- Profile Card -->
                <div class="bg-white box p-3 border-t-4  ">
                    <div class="image overflow-hidden">
                        <img class="profileImage"
                        <spring:message code="profile.img.alt" var="img"/>
                             src="<c:url value="/profile/profile-image/${user.id}"/>"
                             alt="${img}">
                        <div style=" display: flex; justify-content: end;">
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
                    </div>
                    <h1 class="full-name">
                        <c:out value=" ${user.name}" /></h1>
                    <ul>
                        <li class="pt-2">
                            <sec:authorize access="hasRole('BAND')">
                                <spring:message code="profile.bandName"/>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ARTIST')">
                                <spring:message code="profile.firstName"/>
                            </sec:authorize>
                            <c:out value="${user.name}"/>
                        </li>
                        <sec:authorize access="hasRole('ARTIST')">
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.surname"/>
                                <c:out value=" ${user.surname}"/>
                            </li>
                            <hr>
                        </sec:authorize>
                        <hr>
                        <li class="pt-2">
                            <spring:message code="profile.mail"/>
                            <c:out value="${user.email}"/>
                        </li>
                        <hr>
                    </ul>
                    <ul
                            class="status-box hover:text-gray-700 hover:shadow ">
                        <li class="flex items-center py-3">
                            <span><spring:message code="profile.type"/></span>
                            <sec:authorize access="hasRole('ARTIST')">
                            <span class="ml-auto"><span
                                    class="bg-green-500 py-1 px-2 rounded text-white text-sm"><spring:message code="register.artist_word"/> </span></span>
                            </sec:authorize>
                            <sec:authorize access="hasRole('BAND')">
                                <span class="ml-auto"><span
                                        class="bg-green-500 py-1 px-2 rounded text-white text-sm"><spring:message code="register.band_word"/> </span></span>
                            </sec:authorize>
                        </li>
                    </ul>
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
            <div class="w-full md:w-9/12 mx-2 h-64">
                <!-- About Section -->
                <div class="bg-white p-3 shadow-sm rounded-sm">
                    <div class="space-x-2 font-semibold text-gray-900 leading-8">
                        <!-- End of profile card -->
                        <div class="my-4"></div>

                        <!-- Right Side -->
                        <div class=" mx-2 h-70 about-section-profile">

                            <!-- About Section -->
                            <div class="about-section-biografy">

                                <div>
                                <h1 class="auditions-title" ><spring:message code="profile.biography"/> </h1>
                                    <c:if test="${user.description==null}" >
                                            <p><spring:message code="profile.emptyBiography"/> </p>
                                    </c:if>
                                    <c:if test="${!(user.description==null)}" >
                                        <c:out value="${user.description}"/>
                                    </c:if>
                                </div>
                            </div>
                            <!-- End of about section -->
                        </div>
                    </div>
                    <div class="user-data">
                        <div class="about-section-heading">
                            <spring:message code="profile.user.alt" var="userimg"/>
                            <img src="<c:url value="/resources/icons/user.svg"/>" class="user-icon" alt="${userimg}"/>
                            <span class="tracking-wide "><spring:message code="profile.about"/></span>
                        </div>
                        <div>
                            <ul>
                                <li class="pt-2">
                                    <sec:authorize access="hasRole('BAND')">
                                        <spring:message code="profile.bandName"/>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ARTIST')">
                                        <spring:message code="profile.firstName"/>
                                    </sec:authorize>
                                    <c:out value="${user.name}"/>
                                </li>
                                <sec:authorize access="hasRole('ARTIST')">
                                    <hr>
                                    <li class="pt-2">
                                        <spring:message code="profile.surname"/>
                                        <c:out value=" ${user.surname}"/>
                                    </li>
                                    <hr>
                                </sec:authorize>
                                <hr>
                                <li class="pt-2">
                                    <spring:message code="profile.experience"/>
                                    <c:out value="experiencia(MOCK)"/>
                                </li>
                                <hr>
                                <li class="pt-2">
                                    <spring:message code="profile.preferedGenres"/>
                                    <c:out value="generos de preferencia (MOCK)"/>
                                </li>
                                <hr>
                                <li class="pt-2">
                                    <spring:message code="profile.roles"/>
                                    <c:out value="roles que puede cumplir(MOCK)"/>
                                </li>
                                <hr>
                            </ul>
                        </div>
                    </div>
                    <br/>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>