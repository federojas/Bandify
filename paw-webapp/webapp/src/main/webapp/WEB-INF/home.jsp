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
        font-family: 'Questrial', sans-serif;
      }
    </style>
    <script>
      slowScroll = function(target) {
        var scrollContainer = target;
        do { //find scroll container
          scrollContainer = scrollContainer.parentNode;
          if (!scrollContainer) return;
          scrollContainer.scrollTop += 1;
        } while (scrollContainer.scrollTop == 0);

        var targetY = 0;
        do { //find the top of target relatively to the container
          if (target == scrollContainer) break;
          targetY += target.offsetTop;
        } while (target = target.offsetParent);

        scroll = function(c, a, b, i) {
          i++; if (i > 30) return;
          c.scrollTop = a + (b - a) / 32 * i;
          setTimeout(function(){ scroll(c, a, b, i); }, 20);
        }
        // start scrolling
        scroll(scrollContainer, scrollContainer.scrollTop, targetY, 0);
      }
    </script>
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
            <button
              type="button"
              class="section-button border-2 border-white hover:border-black"
              onclick="slowScroll(document.getElementById('posts'))"
            >
              <spring:message code="home.searchingBandsButton"/>
            </button>
            <button
              type="button"
              class="section-button border-2 border-white hover:border-black"
              onclick="slowScroll(document.getElementById('form-post-title'))"
            >
              <spring:message code="home.searchingArtistsButton"/>
            </button>

          </div>
        </div>
          
        </div>
        
      </div>
    </div>
  </body>
</html>
