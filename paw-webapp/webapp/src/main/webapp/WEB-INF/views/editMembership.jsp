<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page
        contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title><spring:message code="title.applicants"/></title>
  <c:import url="../config/generalHead.jsp"/>
  <c:import url="../config/materializeHead.jsp"/>
  <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
  <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />"/>
  <link rel="stylesheet" href="<c:url value="/resources/css/applicants.css" />"/>
  <link rel="stylesheet" href="<c:url value="/resources/css/selectApplicant.css" />"/>
  <script src="<c:url value="/resources/js/pagination.js" />"></script>
  <script src="<c:url value="/resources/js/applicants.js"/>"></script>
  <script src="<c:url value="/resources/js/matMultipleSelect.js"/>"></script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
  <jsp:param name="navItem" value="${2}"/>
  <jsp:param name="name" value="Bandify"/>
</jsp:include>

<main>
  <!-- Auditions content -->
  <div class="applicants-container">
    <div class="left-panel-abs">
      <a class="back-anchor" onclick="window.history.back();" style="cursor: pointer;">
        <div class="back-div">
          <spring:message code="audition.alt.back" var="backAlt"/>
          <img src="<c:url value="/resources/icons/back.svg" />" alt="${backAlt}" class="back-icon"/>
        </div>
      </a>
    </div>
    <div class="applicants-content">
      <h2 class="applicants-title">
        <spring:message code="edit.membership.title"/>
      </h2>

      <div class="confirmation-add-to-band">
                <span class="welcome-title"><spring:message code="edit.confirmation.title"/>
                </span>

        <hr class="rounded">

        <div class="confirmation-container">
          <div class="header-welcome-artist">
                        <span class="welcome-title-2">
                        <spring:message code="edit.confirmation.artist1" arguments="${artistName},${artistSurname}"/>
                        </span>
          </div>

          <c:url value="/profile/editMembership/${membershipId}" var="addToBandActionUrl" />
          <%--@elvariable id="membershipForm" type="ar.edu.itba.paw.webapp.form.MembershipForm"--%>

          <form:form method="post" acceptCharset="utf-8"
                     action="${addToBandActionUrl}" id="membershipForm"
                     modelAttribute="membershipForm"
                     cssClass="gapped-form"
          >
            <div class="roles-selection">
              <form:label class="membership-form-description-label" path="roles">
                <spring:message code="applicants.confirmation.roles"/>
              </form:label>
              <form:select
                      class="multiple-select"
                      path="roles"
                      multiple="true"
              >
                <form:option value="" disabled="true" selected="true"><spring:message code="edituser.lookingFor.maxSelect" arguments="5"/></form:option>
                <c:forEach var="role" items="${roleList}" varStatus="loop">
                  <form:option value="${role.name}"><c:out value="${role.name}"/></form:option>
                </c:forEach>
              </form:select>
              <form:errors path="roles" element="p" cssClass="error">
              </form:errors>
            </div>
            <div class="description-artist">
              <form:label class="membership-form-description-label" path="description">
                <spring:message code="applicants.confirmation.description"/>
              </form:label>
              <spring:message code="audition.form.description.placeholder" arguments="100" var="descriptionplaceholder" />
              <form:textarea
                      maxlength="100" placeholder="${descriptionplaceholder}"
                      class="membership-form-description-textarea"
                      type="text"
                      id="description"
                      path="description"
              />
                <%--                            <p id="emptyDescription" class="error" style="display: none"><spring:message code="NotBlank.auditionForm.description" arguments="0"/> </p>--%>
                <%--                            <p id="longDescription" class="error" style="display: none"><spring:message code="audition.form.description.maxSize" arguments="100"/> </p>--%>
              <form:errors path="description" element="p" cssClass="error">
              </form:errors>
            </div>
            <div class="end-addtoband-btn">
              <button
                      type="submit"
                      value="submit"
                      form="membershipForm"
                      class="artist-profile-btn"
              >
                <spring:message code="edit.confirm"/>
              </button>
            </div>
          </form:form>

        </div>

      </div>


    </div>

  </div>
</main>
<jsp:include page="../components/footer.jsp">
  <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>