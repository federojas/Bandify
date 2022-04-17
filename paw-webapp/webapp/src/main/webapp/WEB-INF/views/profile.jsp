<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <title><spring:message code="navbar.profile" /></title>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/profile.css" />"/>

</head>
<body>
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css" rel="stylesheet">
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
                             src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"
                             alt="">
                    </div>
                    <h1 class="full-name">Jane Doe</h1>

                    <ul
                            class="status-box hover:text-gray-700 hover:shadow ">
                        <li class="flex items-center py-3">
                            <span><spring:message code="profile.type"/></span>
                            <span class="ml-auto"><span
                                    class="bg-green-500 py-1 px-2 rounded text-white text-sm">Artista</span></span>
                        </li>
                        <li class="flex items-center py-3">
                            <span><spring:message code="profile.memberSince"/>
                            </span>
                            <span class="ml-auto">Nov 07, 2016</span>
                        </li>
                    </ul>
                </div>
                <!-- End of profile card -->
                <div class="my-4"></div>
            </div>
            <!-- Right Side -->
            <div class="w-full md:w-9/12 mx-2 h-64">
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
                                <spring:message code="profile.firstName"></spring:message>
                            </li>
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.experience"></spring:message>
                            </li>
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.Education"></spring:message>
                            </li>
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.gender"></spring:message>
                            </li>
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.mail"></spring:message>
                            </li>
                            <hr>
                            <li class="pt-2">
                                <spring:message code="profile.birth"></spring:message>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- End of about section -->

                <div class="my-4"></div>

                <!-- Experience and education -->
                <div class="bg-white p-3 shadow-sm rounded-sm">

                    <div class="grid grid-cols-2">
                        <div>
                            <div class="flex items-center space-x-2 font-semibold text-gray-900 leading-8 mb-3">
                                <span clas="text-indigo-900">
                                    <img src="<c:url value="${pageContext.request.contextPath}/resources/icons/book.svg"/>" class="user-icon" alt="user"/>
                                </span>
                                <span class="tracking-wide"><spring:message code="profile.experience" /></span>
                            </div>
                            <ul class="list-inside space-y-2">
                                <li>
                                    <div class="text-indigo-900">Owner at Her Company Inc.</div>
                                    <div class="text-gray-500 text-xs">March 2020 - Now</div>
                                </li>

                            </ul>
                        </div>
                        <div>
                            <div class="flex items-center space-x-2 font-semibold text-gray-900 leading-8 mb-3">
                                <span clas="text-indigo-900">
                                    <img src="<c:url value="${pageContext.request.contextPath}/resources/icons/education.svg"/>" class="user-icon" alt="user"/>
                                </span>
                                <span class="tracking-wide"><spring:message code="profile.Education" /></span>
                            </div>
                            <ul class="list-inside space-y-2">
                                <li>
                                    <div class="text-indigo-900">Masters Degree in Oxford</div>
                                    <div class="text-gray-500 text-xs">March 2020 - Now</div>
                                </li>
                                <li>
                                    <div class="text-indigo-900">Bachelors Degreen in LPU</div>
                                    <div class="text-gray-500 text-xs">March 2020 - Now</div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- End of Experience and education grid -->
                </div>
                <!-- End of profile tab -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
