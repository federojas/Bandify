<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/profile.css" />"/>
</head>
<body>


<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${6}"/>
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
                             src="<c:url value="/user/${user.id}/profile-image"/>"
                             alt="${img}">
                    </div>
                    <h1 class="full-name">
                        <c:out value=" ${user.name}" /></h1>
                    <ul>
                        <li class="pt-2">
                            <c:if test="${user.band}">
                                <spring:message code="profile.bandName"/>
                            </c:if>
                            <c:if test="${!user.band}">
                                <spring:message code="profile.firstName"/>
                            </c:if>
                            <c:out value="${user.name}"/>
                        </li>
                        <c:if test="${!user.band}">
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.surname"/>
                                <c:out value=" ${user.surname}"/>
                            </li>
                            <hr>
                        </c:if>
                        <hr>
                    </ul>
                    <ul
                            class="status-box hover:text-gray-700 hover:shadow ">
                        <li class="flex items-center py-3">
                            <span><spring:message code="profile.type"/></span>
                            <c:if test="${!user.band}">
                            <span class="ml-auto"><span
                                    class="bg-green-500 py-1 px-2 rounded text-white text-sm"><spring:message code="register.artist_word"/> </span></span>
                            </c:if>
                            <c:if test="${user.band}">
                                <span class="ml-auto"><span
                                        class="bg-green-500 py-1 px-2 rounded text-white text-sm"><spring:message code="register.band_word"/> </span></span>
                            </c:if>
                        </li>
                    </ul>
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
                            <c:if test="${!(user.description==null)}" >
                            <div class="about-section-biografy">
                                <div>
                                    <h1 class="auditions-title" ><spring:message code="profile.biography"/> </h1>
                                        <c:out value="${user.description}"/>
                                </div>
                            </div>
                            </c:if>
                            <!-- End of about section -->
                        </div>
                    </div>
                    <div class="user-data">
                        <div class="about-section-heading">
                            <spring:message code="profile.user.alt" var="userimg"/>
                            <img src="<c:url value="/resources/icons/user.svg"/>" class="user-icon" alt="${userimg}"/>
                            <span class="tracking-wide "><spring:message code="viewProfile.about"/></span>
                        </div>
                        <div>
                            <ul>
                                <li class="pt-2">
                                    <c:if test="${user.band}">
                                        <spring:message code="profile.bandName"/>
                                    </c:if>
                                    <c:if test="${!user.band}">
                                        <spring:message code="profile.firstName"/>
                                    </c:if>
                                    <c:out value="${user.name}"/>
                                </li>
                                <c:if test="${!user.band}">
                                    <hr>
                                    <li class="pt-2">
                                        <spring:message code="profile.surname"/>
                                        <c:out value=" ${user.surname}"/>
                                    </li>
                                    <hr>
                                </c:if>
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