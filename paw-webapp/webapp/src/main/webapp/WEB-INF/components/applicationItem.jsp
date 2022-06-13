<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="title.applicationItem"/> </title>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicationItem.css" />" />
    <script type="text/javascript" src="<c:url value="/resources/js/invertImg.js" />"></script>
</head>
<body>
<li>

    <div class="collapsible-header applicant-header">
        <div class="image-and-name">
            <spring:message code="profile.img.alt" var="img"/>
            <img class="postcard-profile-image"
                 src="<c:url value="/user/${param.userId}/profile-image"/>"
                 alt="${img}">
            <c:if test="${param.available}">
                <spring:message code="available.img.alt" var="available"/>
                <img class="top-image" src="<c:url value="/resources/images/available.png"/>" alt="${available}"/>
            </c:if>
            <span class="user-name">
                    <c:out value="${param.applicantName}"/> <c:out value="${param.applicantSurname}"/>
            </span>
            <div>
                <span class="email-span"><c:out value="(${param.email})"/></span>
            </div>
        </div>
    </div>

    <div class="collapsible-body applicant-body">
        <div>
            <span class="message-span"><c:out value="${param.message}"/></span>
        </div>
        <div class="applicant-body-end">
            <div class="purple-button">
                <c:url value="/user/${param.userId}" var="userUrl"/>
                <a href="${userUrl}" class="usr-url">
                    <spring:message code="applicants.seeProfile"/>
                    <spring:message code="audition.applicants.alt" var="altApplicants"/>
                    <img src="<c:url value="/resources/icons/user.svg"/>" class="audition-icon invert" alt="${altApplicants}" />
                </a>
            </div>
            <c:if test="${param.accepted}">
                <div class="add-to-band-button">
                    <c:url value="/auditions/${param.auditionId}/applicants/select/${param.applicationId}" var="addToBandUrl" />
                    <a href="${addToBandUrl}" onmouseover="invertImg()"
                       onmouseout="invertImgBack()">
                        <button class="artist-profile-btn" id="addToBandBtn">
                            <spring:message code="applicants.addToBand"/>
                            <spring:message code="audition.applicants.alt" var="altAddToBand"/>
                            <img src="<c:url value="/resources/icons/add-user.png"/>"
                                 id="addToBandBtnImg"
                                 class="audition-icon" alt="${altAddToBand}" />
                        </button>
                    </a>
                </div>
            </c:if>
            <c:if test="${param.actionable}">
            <div>
                <spring:message code="application.accept" var="accept"/>
                <spring:message code="application.reject" var="reject"/>
                <div class="application-icons">
                    <c:url value="/auditions/${param.auditionId}" var="acceptUrl">
                        <c:param name="accept" value="true"/>
                        <c:param name="userId" value="${param.userId}"/>
                    </c:url>
                    <c:url value="/auditions/${param.auditionId}" var="rejectUrl">
                        <c:param name="accept" value="false"/>
                        <c:param name="userId" value="${param.userId}"/>
                    </c:url>
                    <div class="application-confirmation-buttons">
                        <form action="${acceptUrl}" method="post">
                            <button type="submit"><img src="<c:url value="/resources/icons/success.svg" />" alt="${accept}" class="application-icon"/></button>
                        </form>
                            <button onclick="openConfirmation()" ><img src="<c:url value="/resources/icons/reject.svg" />" alt="${reject}" class="application-icon"/></button>
                    </div>
                </div>
            </div>
            </c:if>
        </div>
    </div>
    <spring:message code="rejectConfirmationModal.title" var="modalTitle"/>
    <spring:message code="rejectConfirmationModal.rejectApplicant" var="modalHeading"/>
    <spring:message code="rejectConfirmationModal.confirmationQuestion" var="confirmationQuestion"/>
    <jsp:include page="../components/confirmationModal.jsp">
        <jsp:param name="modalTitle" value="${modalTitle}" />
        <jsp:param name="isDelete" value="${true}" />
        <jsp:param name="modalHeading" value="${modalHeading}" />
        <jsp:param name="confirmationQuestion" value="${confirmationQuestion}" />
        <jsp:param name="action" value="${rejectUrl}" />
    </jsp:include>
</li>

</body>
</html>
