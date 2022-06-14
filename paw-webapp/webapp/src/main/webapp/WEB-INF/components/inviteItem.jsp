<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
  <title><spring:message code="title.inviteItem"/> </title>
  <link rel="stylesheet" href="<c:url value="/resources/css/applicationItem.css" />" />
  <script type="text/javascript" src="<c:url value="/resources/js/invertImg.js" />"></script>
</head>
<body>
<li>

  <div class="collapsible-header applicant-header">
    <div class="image-and-name">
      <spring:message code="profile.img.alt" var="img"/>
      <img class="postcard-profile-image"
           src="<c:url value="/user/${param.bandId}/profile-image"/>"
           alt="${img}">
      <span class="user-name">
                    <c:out value="${param.bandName}"/>
            </span>
      <div>
      </div>
    </div>
  </div>

  <div class="collapsible-body applicant-body">
    <div>
      <span class="message-span"><c:out value="${param.inviteDescription}"/></span>
    </div>
    <div class="roles-div my-5">
      <c:forEach var="role" items="${requestScope.memberRoles}">
          <span class="roles-span"><c:out value="${role.name}"/></span>
      </c:forEach>
    </div>
    <div class="applicant-body-end">
      <div class="purple-button">
        <c:url value="/user/${param.bandId}" var="userUrl"/>
        <a href="${userUrl}" class="usr-url">
          <spring:message code="applicants.seeProfile"/>
          <spring:message code="audition.applicants.alt" var="altApplicants"/>
          <img src="<c:url value="/resources/icons/user.svg"/>" class="audition-icon invert" alt="${altApplicants}" />
        </a>
      </div>
      <div>
        <spring:message code="application.accept" var="accept"/>
        <spring:message code="application.reject" var="reject"/>
        <div class="application-icons">
          <c:url value="/invites/${param.membershipId}" var="acceptUrl">
            <c:param name="accept" value="true"/>
          </c:url>
          <c:url value="/invites/${param.membershipId}" var="rejectUrl">
            <c:param name="accept" value="false"/>
          </c:url>
          <div class="application-confirmation-buttons">
            <form action="${acceptUrl}" method="post">
              <button type="submit"><img src="<c:url value="/resources/icons/success.svg" />" alt="${accept}" class="application-icon"/></button>
            </form>
            <form action="${rejectUrl}" method="post">
              <button onclick="openConfirmation()" ><img src="<c:url value="/resources/icons/reject.svg" />" alt="${reject}" class="application-icon"/></button>
            </form>
          </div>
        </div>
      </div>

    </div>
  </div>

</li>

</body>
</html>
