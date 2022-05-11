<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="deleteConfirmationModal.title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/confirmationModal.css" />" />
    <script src="<c:url value="/resources/js/alerts.js" />"></script>

</head>
<body onload="confirmationModalLoad()">

<div  class="audition-delete-btn">
    <button class="audition-btn" onclick="openConfirmation()" type="submit">
        <spring:message code="audition.alt.delete" var="delete"/>
        <spring:message code="audition.delete" />
        <img src="<c:url value="/resources/icons/trash.svg"/>" class="audition-icon" alt="${delete}"/>
    </button>
</div>
<div id="confirmationModal" class="confirmation-modal">
    <span onclick="closeConfirmationModal()" class="close">×</span>
    <div class="modal-content" >
        <div class="modal-container">
            <h1><spring:message code="deleteConfirmationModal.deleteAudition"/> </h1>
            <p><spring:message code="deleteConfirmationModal.confirmationQuestion"/></p>

            <div class="button-container">
                <div>
                <form  action="/profile/deleteAudition/${audition.id}" method="post">
                    <button type="submit" class="audition-delete-confirm-btn">
                        <spring:message code="audition.alt.delete" var="delete"/>
                        <spring:message code="deleteConfirmationModal.confirm"/>
                        <img src="<c:url value="/resources/icons/trash.svg"/>" class="audition-icon" alt="${delete}"/>
                    </button>
                </form>
                </div>
                <div>
                    <button type="button" onclick=" closeConfirmationModal()" class="audition-cancel-confirm-btn"><spring:message code="deleteConfirmationModal.cancel"/> </button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
