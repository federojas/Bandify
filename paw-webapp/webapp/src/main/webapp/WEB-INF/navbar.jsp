<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Questrial">
    <style>
        nav {
            background-color: #1c041c;
            padding: 0.75rem 1rem;
            display: flex;
            align-items: center;
        }
        .container {
            width: 100%;
        }
        @media (min-width: 640px) {
            .container {
                max-width: 640px;
            }
        }
        @media (min-width: 768px) {
            .container {
                max-width: 768px;
            }
        }
        @media (min-width: 1024px) {
            .container {
                max-width: 1024px;
            }
        }
        @media (min-width: 1280px) {
            .container {
                max-width: 1280px;
            }
        }
        @media (min-width: 1536px) {
            .container {
                max-width: 1536px;
            }
        }
        body {
            font-family: 'Questrial', sans-serif;
        }
        .nav-div {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
            margin: 0 auto;
        }
        .bandify-title {
            align-self: center;
            font-size: 2rem;
            line-height: 2.25rem;
            font-weight: 600;
            padding: 0.5rem;
            white-space: nowrap;
            --tw-text-opacity: 1;
            color: rgb(255 255 255 / var(--tw-text-opacity));
        }

        .bandify-logo {
            width: 2.5rem;
        }

        #mobile-menu > ul {
            display: flex;
            flex-direction: column;
            margin-top: 1rem;
        }

        @media (min-width: 768px) {
                    #mobile-menu > ul {
                        font-weight: 500;
                        flex-direction: row;
                        font-size: 0.875rem/* 14px */;
                        line-height: 1.25rem/* 20px */;
                        margin-top: 0px;
                    }
        }

        #mobile-menu > ul > li {
            margin: 0 0.5rem;
        }
    </style>
</head>
<body>
<nav>
    <div class="container nav-div">
        <a href="<c:url value="/"/>" class="flex items-center">
            <spring:message code="img.alt.logo" var="bandify" />
            <img
                    src="public/images/logo.png"
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
                            href="<c:url value="/"/>"
                            class="${param.navItem == 1? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.home"/></a
                    >
                </li>
                <li>
                    <a
                            href="<c:url value="/auditions"/>"
                            class="${param.navItem == 2? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.auditions"/></a
                    >
                </li>
                <li>
                    <a
                            href="<c:url value="/newAudition"/>"
                            class="${param.navItem == 3? "block py-2 pr-4 pl-3 text-white font-black rounded text-2xl" : "block py-2 pr-4 pl-3 text-white rounded text-2xl" }"
                    ><spring:message code="navbar.post"/></a
                    >
                </li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
