<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/navbar.css" />" />
</head>
<body>
<nav>
    <div class="container nav-div">
        <a href="<c:url value="/"/>" class="flex items-center">
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
                <c:if test="${user != null}">
                <li>
                    <a
                            href="<c:url value="/auditions"/>"
                            class="${param.navItem == 2? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.auditions"/></a
                    >
                </li>
                </c:if>
                <c:if test="${user.isBand()}">
                    <li>
                        <a
                                href="<c:url value="/newAudition"/>"
                                class="${param.navItem == 3? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                        ><spring:message code="navbar.post"/></a
                        >
                    </li>
                </c:if>
                <c:if test="${user != null}">
                <li>
                    <a
                            href="<c:url value="/profile"/>"
                            class="${param.navItem == 4? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.profile"/></a
                    >
                </li>
                </c:if>
                <c:if test="${user == null }">
                <li>
                    <a
                            href="<c:url value="/profile"/>"
                            class="${param.navItem == 4? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.aboutUs"/></a
                    >
                </li>
                </c:if>

            </ul>
        </div>
    </div>
</nav>
</body>
</html>
