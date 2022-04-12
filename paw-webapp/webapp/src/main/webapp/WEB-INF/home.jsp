<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

  <head>
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

      <h2 class="text-5xl section-title mt-20 mb-5" id="posts">
        <spring:message code="home.auditionsSection" />
      </h2>
      <%--Publicaciones de audiciones--%>
      <div class="posts">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
          <c:set
            var="lookingFor"
            value="${audition.lookingFor}"
            scope="request"
          />
          <c:set
            var="musicGenres"
            value="${audition.musicGenres}"
            scope="request"
          />
          <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="${audition.id}" />
            <jsp:param name="postCard" value="${1}" />
            <jsp:param name="auditionDate" value="${audition.timeElapsed}" />
            <jsp:param name="auditionTitle" value="${audition.title}" />
            <jsp:param name="auditionEmail" value="${audition.email}" />
            <jsp:param
              name="auditionLocation"
              value="${audition.location.name}"
            />
            <jsp:param
              name="auditionDescription"
              value="${audition.description}"
            />
          </jsp:include>
        </c:forEach>
      </div>

      <%--Formulario--%>
      <div class="post-form-container" id="form-post">
        <h1 class="home-h1-0"><spring:message code="home.formSectionh1"/></h1>
        <h2 class="home-h1-0"><spring:message code="home.formSectionh2"/></h2>
        <c:url value="/create" var="postPath" />
        <!-- Form box -->
        <div class="inner-box-form" id="form-post-title">
          <form:form
            modelAttribute="auditionForm"
            action="${postPath}"
            method="post"
            acceptCharset="utf-8"
          >
            <div>
              <form:label class="home-form-label" path="title">
                <spring:message code="home.form.title"/>
              </form:label>
              <form:input type="text"  maxlength="25" placeholder="(max 25 caracteres)" class="home-form-input" path="title" />

              <form:errors path="title" element="p" cssClass="error"> </form:errors>
            </div>
            <div>
              <form:label class="home-form-label" path="description">
                <spring:message code="home.form.description"/>
              </form:label>
              <form:textarea
                      maxlength="300" placeholder="(max 300 caracteres)"
                class="home-form-input"
                type="text"
                path="description"
              />

              <form:errors path="description" element="p" cssClass="error"> </form:errors>
            </div>
            <div>
              <form:label class="home-form-label" path="location">
                <spring:message code="home.form.location"/>
              </form:label>
              <form:select
                class="home-form-input"
                path="location"
                multiple="false"

              >
                <form:option value="0" selected="true" disabled="disabled" hidden="true"><spring:message code="home.form.location.default"/></form:option>
                <c:forEach
                  var="location"
                  items="${locationList}"
                  varStatus="loop"
                >
                  <form:option value="${location.id}">
                    ${location.name}
                  </form:option>
                </c:forEach>
              </form:select>
            </div>
            <div>
              <form:label class="home-form-label" path="email">
                <spring:message code="home.form.email"/>
              </form:label>
              <form:input type="text"  maxlength="50" placeholder="ejemplo@email.com" class="home-form-input" path="email" />

              <form:errors path="email" element="p" cssClass="error"> </form:errors>
            </div>
            <div>
              <label class="home-form-label" for="musicGenres"> <spring:message code="home.form.musicGenres"/> </label>
              <form:select
                class="multiple-select home-form-input"
                path="musicGenres"
                multiple="true"
              >
                <c:forEach var="genre" items="${genreList}" varStatus="loop">
                  <form:option value="${genre.id}"> ${genre.name} </form:option>
                </c:forEach>
              </form:select>
            </div>
            <div>
              <label class="home-form-label" for="lookingFor"> <spring:message code="home.form.lookingFor"/> </label>
              <form:select
                class="multiple-select home-form-input"
                path="lookingFor"
                multiple="true"
              >
                <c:forEach var="role" items="${roleList}" varStatus="loop">
                  <form:option value="${role.id}"> ${role.name} </form:option>
                </c:forEach>
              </form:select>
            </div>
            <div class="post-button-div mt-8">
              <button
                type="submit"
                class="post-button bg-sky-600 hover:bg-sky-700"
              >
                <spring:message code="home.postButton"/>
              </button>
            </div>
          </form:form>
        </div>
      </div>
    </div>
  </body>
</html>
