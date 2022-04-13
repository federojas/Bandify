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
  </head>
  <body class="bg-gray-100">
    <%--Navbar--%>
    <jsp:include page="navbar2.jsp">
      <jsp:param name="navItem" value="${1}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <!-- Content -->
    <div class="content">
      <!-- Hero -->
      <div class="guitar-hero">
        <img src="public/images/guitar.png" alt="guitar" />
        <div class="hero-1">
          <span class="font-bold text-4xl"
            ><spring:message code="home.slogan1"/></span
          >
          <p class="text-lg mt-3"><spring:message code="home.slogan2"/></p>
          <div class="hero-2">
            <span class="text-xl ml-2"><spring:message code="home.options"/></span>
          <div class="buttons">
            <a href="<c:url value="/auditions"/> ">
              <button
                type="button"
                class="section-button border-2 border-white hover:border-black"
              >
                <spring:message code="home.searchingBandsButton"/>
              </button>
            </a>
            <a href="<c:url value="/newAudition"/> ">
              <button
                type="button"
                class="section-button border-2 border-white hover:border-black"
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
