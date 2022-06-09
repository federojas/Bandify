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
    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />" />
    <script src="<c:url value="/resources/js/share.js" />"></script>
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

    <main>
      <div class="audition-content">
        <div class="left-panel">
          <a class="back-anchor" href="<c:url value="/auditions" />">
            <div class="back-div">
              <spring:message code="audition.alt.back" var="backAlt"/>
              <img src="<c:url value="/resources/icons/back.svg"/>" alt="${backAlt}" class="back-icon"/>
            </div>
          </a>
        </div>

        <div class="card-extern">

          <!--content-->
          <div class="card-content">
            <a href="<c:url value="/user/${user.id}"/>">
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
                        <span class="roles-span"><c:out value="${item.name}" /></span>
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
                        <span class="genre-span"><c:out value="${item.name}" /></span>
                      </c:forEach>
                    </div>
                  </li>
                </ul>
              </div>
              <sec:authorize access="hasRole('ARTIST')">
                <c:if test="${!alreadyApplied}">
                  <div class="audition-application">
                    <jsp:include page="../components/applicationForm.jsp">
                      <jsp:param name="auditionForm" value="${1}" />
                      <jsp:param name="auditionFormId" value="${audition.id}" />
                    </jsp:include>
                  </div>
                </c:if>
              </sec:authorize>
            </div>
          </div>
        </div>

        <div class="right-panel">
          <div class="buttonry">
            <a class="audition-applicants-btn hover: shadow-sm">
              <button class="audition-btn" onclick="share()">
                <spring:message code="audition.share" />
                <spring:message code="audition.share.alt" var="altShare"/>
                <img src="<c:url value="/resources/icons/copy.svg"/>" class="audition-icon invert" alt="${altShare}" />
              </button>
            </a>
            <c:if test="${isOwner}">
              <a class="audition-applicants-btn hover: shadow-sm" href="<c:url value="/auditions/${audition.id}/applicants"/>">
                <button class="audition-btn" type="submit">
                  <spring:message code="audition.applicants" />
                  <spring:message code="audition.applicants.alt" var="altApplicants"/>
                  <img src="<c:url value="/resources/icons/user.svg"/>" class="audition-icon invert" alt="${altApplicants}" />
                </button>
              </a>
              <a class="audition-edit-btn hover: shadow-sm" href="<c:url value="/profile/editAudition/${audition.id}"/>">
                <button class="audition-btn" type="submit">
                  <spring:message code="audition.alt.edit" var="edit"/>
                  <spring:message code="audition.edit" />
                  <img src="<c:url value="/resources/icons/edit-white-icon.svg"/>" class="audition-icon" alt="${edit}"/>
                </button>
              </a>
              <a class="audition-delete-btn">
                <button class="audition-btn" onclick="openConfirmation()" type="submit">
                  <spring:message code="audition.alt.delete" var="delete"/>
                  <spring:message code="audition.delete" />
                  <img src="<c:url value="/resources/icons/trash.svg"/>" class="audition-icon" alt="${delete}"/>
                </button>
              </a>
              <spring:message code="deleteConfirmationModal.title" var="modalTitle"/>
              <spring:message code="deleteConfirmationModal.deleteAudition" var="modalHeading"/>
              <spring:message code="deleteConfirmationModal.confirmationQuestion" var="confirmationQuestion"/>
              <c:url value="/profile/deleteAudition/${audition.id}" var="postPath"/>
              <jsp:include page="../components/confirmationModal.jsp">
                <jsp:param name="modalTitle" value="${modalTitle}" />
                <jsp:param name="isDelete" value="${true}" />
                <jsp:param name="modalHeading" value="${modalHeading}" />
                <jsp:param name="confirmationQuestion" value="${confirmationQuestion}" />
                <jsp:param name="action" value="${postPath}" />
              </jsp:include>
            </c:if>
          </div>
        </div>
      </div>
      <div id="snackbar-copy"><spring:message code="snackbar.copy.message"/></div>
    </main>
    <jsp:include page="../components/footer.jsp">
      <jsp:param name="name" value="Bandify"/>
    </jsp:include>
  </body>
</html>
