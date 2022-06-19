<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/navbar.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/profile.css" />"/>
    <title><spring:message code="title.navBar"/> </title>
</head>
<body>
<nav>
    <div class="nav-container nav-div">
        <span id="langspan" style="display: none;"><spring:message code="app.lang"/></span>
        <a href="<c:url value="/"/>" class="logo-section">
            <spring:message code="img.alt.logo" var="bandify" />
            <img
                    src=
                    "<c:url value="/resources/images/logo.png" />"

                    class="bandify-logo"
                    alt="${bandify}"
            />
            <span
                    class="bandify-title"
            ><spring:message code="navbar.bandify"/></span
            >
        </a>

        <div class="w-full md:block md:w-auto" id="mobile-menu">
            <ul>

                <li>
                    <a
                            href="<c:url value="/auditions"/>"
                            class="${param.navItem == 2? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.auditions"/></a
                    >
                </li>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <a
                                href="<c:url value="/users"/>"
                                class="${param.navItem == 7? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                        ><spring:message code="navbar.users"/></a
                        >
                    </li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">

                    <li>
                        <a
                                href="<c:url value="/aboutUs"/>"
                                class="${param.navItem == 4? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                        ><spring:message code="navbar.aboutUs"/></a
                        >
                    </li>

                    <c:if test="${param.navItem != 0 && param.navItem != 5}">
                        <li>
                            <div class="flex">
                                <a href="<c:url value="/login" />"
                                   class="purple-login-button">
                                    <spring:message code="navbar.login" />
                                </a>
                            </div>
                        </li>
                    </c:if>

                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasRole('BAND')">
                        <li>
                            <a
                                    href="<c:url value="/newAudition"/>"
                                    class="${param.navItem == 3? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                            ><spring:message code="navbar.post"/></a
                            >
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ARTIST')">
                        <li>
                            <a
                                    href="<c:url value="/profile/applications"/>"
                                    class="${param.navItem == 9? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                            >
                                <spring:message code="navbar.manager" var="manager"/>
                                <img src="<c:url value="/resources/icons/manager.svg" />"
                                     class="profile-icon-img" alt="${manager}"/>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('BAND')">
                        <li>
                            <a
                                    href="<c:url value="/profile/auditions"/>"
                                    class="${param.navItem == 9? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                            >
                                <spring:message code="navbar.manager" var="manager"/>
                                <img src="<c:url value="/resources/icons/manager.svg" />"
                                     class="profile-icon-img" alt="${manager}"/>
                            </a>
                        </li>
                    </sec:authorize>


                    <li>
                        <a
                                href="<c:url value="/profile"/>"
                                class="${param.navItem == 4? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                        >
                            <spring:message code="navbar.profile" var="profile"/>
                            <img src="<c:url value="/resources/icons/user.svg"/>"
                                alt="${profile}"
                                class="profile-icon-img"
                            />
                        </a
                        >
                    </li>

                    <li>
                    <div class="logout flex ml-4">
                        <a href="<c:url value="/logout" />" class="block py-2 pr-4 pl-3 text-white font-black rounded text-2xl">
                            <spring:message code="profile.logout" var="logout"/>
                            <img src="<c:url value="/resources/icons/logout.svg" />"
                                 alt="${logout}"
                                 class="icon-img"
                                 />
                        </a>
                    </div>


                    </li>
                </sec:authorize>


            </ul>
        </div>
    </div>
</nav>
</body>
</html>
