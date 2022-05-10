<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
  <head>
    <title><spring:message code="title.audition"/></title>
    <c:import url="../config/generalHead.jsp" />
    <c:import url="../config/materializeHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/postCard.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/audition.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/modals.css" />" />
    <script>
      $(document).ready(function(){
        $('.modal').modal();
      });
    </script>
  </head>
  <body class="flex flex-col">
    <%--Navbar--%>
    <jsp:include page="../components/navbar.jsp">
      <jsp:param name="navItem" value="${2}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <div class="audition-content">

      <div class="card-extern">

        <!--content-->
        <div class="card-content">
          <a href="/user/${user.id}">
            <div class="audition-profile">
                <div class="image overflow-hidden">
                    <img class="audition-profile-image"
                    <spring:message code="profile.img.alt" var="img"/>
                         src="<c:url value="/user/${user.id}/profile-image"/>"
                         alt="${img}">
                </div>
                <h1 class="audition-band-name"><c:out value=" ${user.name}" /></h1>
            </div>
          </a>

          <!--header-->
          <div class="card-header">
            <h3 class="title">
              <c:out value="${audition.title}" />
            </h3>
          </div>
          <!--body-->
          <div class="card-body">
            <div class="even-columns">
              <!-- Left body part (info) -->
              <div class="card-info">
                <ul>
                    <li class="info-item">
                      <b> <spring:message code="audition.about"/> </b>
                      <br />
                      <p class="description">
                        &nbsp;&nbsp;<c:out value="${audition.description}" />
                      </p>
                    </li>
                  <li class="info-item">
                    <b> <spring:message code="audition.location"/> </b>
                    <br />
                    <div class="tag">
                      <c:out value="${audition.location.name}" />
                    </div>
                  </li>
                </ul>
              </div>
              <!-- Right body part (form) -->
              <sec:authorize access="hasRole('ARTIST')">
                <div class="audition-application">
                  <jsp:include page="../components/applicationForm.jsp">
                    <jsp:param name="auditionForm" value="${1}" />
                    <jsp:param name="auditionFormId" value="${audition.id}" />
                  </jsp:include>
                </div>
              </sec:authorize>
            </div>
            <div class="card-info">
              <ul>
                <li class="info-item">
                  <b> <spring:message code="audition.desired"/> </b>
                  <br />
                  <div class="tag-list">
                    <c:forEach
                            var="item"
                            items="${audition.lookingFor}"
                            varStatus="loop"
                    >
                      <div class="tag">${item.name}</div>
                    </c:forEach>
                  </div>
                </li>
                <li class="info-item">
                  <b> <spring:message code="audition.genres"/> </b>
                  <br />
                  <div class="tag-list">
                    <c:forEach
                            var="item"
                            items="${audition.musicGenres}"
                            varStatus="loop"
                    >
                      <div class="tag">${item.name}</div>
                    </c:forEach>
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <c:if test="${isOwner}">
            <div class="buttonry">
              <a class="audition-edit-btn hover: shadow-sm" href="/profile/editAudition/${audition.id}">
                <button class="audition-btn" type="submit">
                  <spring:message code="audition.alt.edit" var="edit"/>
                    <spring:message code="audition.edit" />
                    <img src="<c:url value="/resources/icons/edit-white-icon.svg"/>" class="audition-icon" alt="${edit}"/>
                </button>
              </a>
              <form class="audition-delete-btn" action="/profile/deleteAudition/${audition.id}" method="post">
                 <button class="audition-btn" type="submit">
                  <spring:message code="audition.alt.delete" var="delete"/>
                    <spring:message code="audition.delete" />
                    <img src="<c:url value="/resources/icons/trash.svg"/>" class="audition-icon" alt="${delete}"/>
                </button>
              </form>
            </div>
          </c:if>
        </div>
      </div>
    </div>
    <div class="back-auditions-div">
      <a class="back-anchor" href="<c:url value="/auditions" />">
        <spring:message code="success.link"/>
      </a>
    </div>
  </body>
</html>
