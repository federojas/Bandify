<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <title><spring:message code="navbar.profile" /></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/profile.css" />"/>

</head>
    <body>
        <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>


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
                        <div class="bg-white p-3 border-t-4  bg-main-color">
                            <div class="image overflow-hidden">
                                <img class="h-auto w-full mx-auto"
                                     src="<c:url value="${pageContext.request.contextPath}/resources/images/profile_anon.png"/>"
                                     alt="">
                            </div>
                            <h1 class="full-name">
                                <c:out value=" ${user.name}" /></h1>

                            <ul
                                    class="status-box hover:text-gray-700 hover:shadow ">
                                <li class="flex items-center py-3">
                                    <span><spring:message code="profile.type"/></span>
                                    <c:if test="${!user.isBand()}">


                            <span class="ml-auto"><span
                                    class="bg-green-500 py-1 px-2 rounded text-white text-sm"><spring:message code="register.artist_word"></spring:message> </span></span>
                                    </c:if>
                                    <c:if test="${user.isBand()}">
                                <span class="ml-auto"><span
                                        class="bg-green-500 py-1 px-2 rounded text-white text-sm"><spring:message code="register.band_word"></spring:message> </span></span>
                                    </c:if>
                                </li>
<%--                                <li class="flex items-center py-3">--%>
<%--                            <span><spring:message code="profile.memberSince"/>--%>
<%--                            </span>--%>
<%--                                    <span class="ml-auto">Nov 07, 2016</span>--%>
<%--                                </li>--%>
                            </ul>
                        </div>
                        <!-- End of profile card -->
                        <div class="profile-btns">
                            <a href="<c:url value="/profile/edit" />">
                                <button class="edit-btn">
                                    <img src="/resources/icons/edit.svg"
                                        alt="edit"
                                         class="icon-img"
                                    />
                                    <spring:message code="profile.editProfile"/>
                                </button>
                            </a>
                            <a href="<c:url value="/logout"/>" >
                                <button class="logout-btn">
                                    <img src="/resources/icons/logout.svg"
                                        alt="logout"
                                         class="icon-img"/>
                                    <spring:message code="profile.logout"/>
                                </button>
                            </a>
                        </div>
                    </div>
                    <!-- Right Side -->
                    <div class="w-full md:w-9/12 mx-2 h-64">
                        <!-- About Section -->
                        <div class="bg-white p-3 shadow-sm rounded-sm">
                            <div class="flex items-center space-x-2 font-semibold text-gray-900 leading-8">



                <!-- End of profile card -->
                <div class="my-4"></div>

            <!-- Right Side -->
            <div class="w-full md:w-9/12 mx-2 h-70">
                <!-- About Section -->
                <div class="bg-white p-3 shadow-sm rounded-sm">
                    <div class="flex items-center space-x-2 font-semibold text-gray-900 leading-8">
                        <span clas="text-indigo-900">
                              <img src="<c:url value="${pageContext.request.contextPath}/resources/icons/user.svg"/>" class="user-icon" alt="user"/>
                                </span>
                                <span class="tracking-wide "><spring:message code="profile.about"></spring:message></span>
                            </div>
                    <div class="text-gray-700 flex justify-start">
                        <ul>
                            <li class="pt-2">
                            <c:if test="${user.isBand()}">

                                    <spring:message code="profile.bandName"></spring:message>


                            </c:if>
                            <c:if test="${!user.isBand()}">

                                <spring:message code="profile.firstName"></spring:message>


                            </c:if>
                          <c:out value="${user.getName()}"/>
                            </li>
                            <c:if test="${!user.isBand()}">
                            <hr>
                                <li class="pt-2">
                                    <spring:message code="profile.surname"></spring:message>
                                       <c:out value=" ${user.getSurname()}"/>
                                </li>
                                <hr>
                            </c:if>

                        <%--                            <li class="pt-2">--%>
<%--                                <spring:message code="profile.experience"></spring:message> ---%>
<%--                            </li>--%>
<%--                            <hr>--%>
<%--                            <li class="pt-2">--%>
<%--                                <spring:message code="profile.Education"></spring:message> ---%>
<%--                            </li>--%>
<%--                            <hr>--%>
<%--                            <li class="pt-2">--%>
<%--                                <spring:message code="profile.gender"></spring:message> ---%>
<%--                            </li>--%>
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.mail"></spring:message>
                               <c:out value="${user.getEmail()}"/>
                            </li>
<%--                            <hr>--%>
<%--                            <li class="pt-2">--%>
<%--                                <spring:message code="profile.birth"></spring:message> ---%>
<%--                            </li>--%>
                        </ul>
                    </div>
                        </div>
                        <!-- End of about section -->

                        </span>

                    </div>
                </div>
                <!-- End of about section -->

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>