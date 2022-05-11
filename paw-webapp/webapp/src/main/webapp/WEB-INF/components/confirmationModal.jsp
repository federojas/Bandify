<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>${param.modalTitle}</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/confirmationModal.css" />" />
    <script src="<c:url value="/resources/js/alerts.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/modals.css" />" />


</head>
<body onload="confirmationModalLoad()">
<div id="confirmationModal" class="confirmation-modal">
    <span onclick="closeConfirmationModal()" class="close">×</span>
    <div class="modal-content" >
        <div class="modal-container">
            <h1>${param.modalHeading}</h1>
            <p>${param.confirmationQuestion}</p>

            <div class="button-container">
                <div>
                    <button type="button" onclick=" closeConfirmationModal()" class="audition-cancel-confirm-btn"><spring:message code="confirmationModal.cancel"/> </button>
                </div>
                <div>
                        <c:if test="${param.isDelete}">
                            <form  action="<c:url value="${param.action}"/>" method="post">
                                    <button type="submit" class="audition-delete-confirm-btn">
                                        <spring:message code="audition.alt.delete" var="delete"/>
                                        <spring:message code="confirmationModal.confirm"/>
                                            <img src="<c:url value="/resources/icons/trash.svg"/>" class="audition-icon" alt="${delete}"/>
                                    </button>
                            </form>
                        </c:if>
                        <c:if test="${!param.isDelete}">
                            <button type="submit" class="audition-confirm-btn " onclick="closeConfirmationModal()">
                                <spring:message code="confirmationModal.confirm"/>
                            </button>
                        </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
