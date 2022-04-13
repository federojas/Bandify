<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
  <head>
    <link rel="icon" type="image/png" href="public/images/logo.png" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Questrial">
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><spring:message code="home.title"/></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css" />
    <style>
        body {
            /* gray-100 */
            --tw-bg-opacity: 1;
            background-color: rgb(243 244 246 / var(--tw-bg-opacity));
        }
        .content {
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
        }
        .guitar-hero {
            position: relative;
        }
        .hero-title {
            position: absolute;
            top: 10%;
            left: 5%;
            color: #efefef;
            width: 50%;
        }
        .hero-title > span {
            font-weight: 700;
            font-size: 2.5rem;
            line-height: 2.75rem;
        }
        .hero-title > p {
            font-size: 1.5rem/* 18px */;
            line-height: 1.75rem/* 28px */;
            margin-top: 1rem;
        }
        .hero-buttons {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            margin-top: 3rem;
            font-size: 1.5rem/* 18px */;
            line-height: 1.75rem/* 28px */;
        }
        .buttons {
            display: flex;
            flex-direction: row;
            justify-content: flex-start;
        }
        .purple-hover-button {
            padding: 0.5rem 1.25rem;
            color: #ffffff;
            font-weight: 600;
            border-radius: 9999px;
            background-color: #1c041c !important;
            margin: 1rem;
        }
        .purple-hover-button:nth-child(1n) {
            margin-left: 0;
        }
        .purple-hover-button:nth-child(2n) {
            margin-right: 0;
        }
        .purple-hover-button:hover {
            border-width: 2px;
            border-color: #ffffff;
        }
    </style>
  </head>
  <body>
    <!-- Navbar -->
    <jsp:include page="navbar2.jsp">
      <jsp:param name="navItem" value="${1}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <!-- Content -->
    <div class="content">
      <!-- Hero -->
      <div class="guitar-hero">
        <img src="public/images/guitar.png" alt="guitar" />
        <div class="hero-title">
            <span><spring:message code="home.slogan1"/></span>
            <p><spring:message code="home.slogan2"/></p>
            <div class="hero-buttons"> 
                <p><spring:message code="home.options"/></p>
                <div class="buttons">
                    <a href="<c:url value="/auditions"/> ">
                    <button
                        type="button"
                        class="purple-hover-button "
                    >
                        <spring:message code="home.searchingBandsButton"/>
                    </button>
                    </a>
                    <a href="<c:url value="/newAudition"/> ">
                    <button
                        type="button"
                        class="purple-hover-button"
                    >
                        <spring:message code="home.searchingArtistsButton"/>
                    </button>
                    </a>
                </div>
            </div> 
        </div> 
      </div>
    </div>
  </body>
</html>
