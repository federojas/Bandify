<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.editprofile"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>

    <title><spring:message code="edituser.title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/profile.css" />"/>
    <script src="<c:url value="/resources/js/editProfile.js"/>"></script>
    <script src="<c:url value="/resources/js/matMultipleSelect.js"/>"></script>
    <script src="<c:url value="/resources/js/editCancelButton.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/alerts.js"/>"></script>
</head>
<body onload="editBandFormCheck()">
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${6}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>


<main class="flex flex-col justify-center">
    <h1 class="editProfile-title" id="title"><spring:message code="edituser.title" /></h1>
    <div class="editProfile-box" id="form">
        <c:url value="/profile/editBand" var="editProfileUrl"/>

        <%--@elvariable id="bandEditForm" type="ar.edu.itba.paw.webapp.form.BandEditForm"--%>
        <form:form method="post" acceptCharset="utf-8" modelAttribute="bandEditForm"
                   action="${editProfileUrl}" id="bandEditForm" enctype="multipart/form-data">
            <div>
                <form:label class="form-label" path="name">
                    <spring:message code="register.form.name"/>
                </form:label>
                <spring:message code="register.form.nameplaceholder" var="nameplaceholder"/>
                <form:input type="text" id="bandName" maxlength="50" placeholder="${nameplaceholder}" class="form-input"
                            path="name"/>
                <form:errors path="name" element="p" cssClass="error"> </form:errors>
                <p class="error" id="wrongArtistName" style="display: none"><spring:message
                        code="register.form.invalidName"/></p>

            </div>

            <div>
                <form:label class="form-label" path="profileImage" >
                    <spring:message code="editProfile.form.image"/>
                </form:label>
                <br/>
                <div class = "editProfilePicture">
                    <spring:message code="profile.img.alt" var="img"/>
                    <img id="imagePreview" src="<c:url value="/user/${user.id}/profile-image"/>" class="profileImage" alt="${img}"/>
                    <div class="edit-image-text-container">
                        <form:input id="selectImage" type="file" path="profileImage" accept="image/png, image/jpeg" onchange="previewImage()" />
                        <form:errors path="profileImage" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>

            <div>
                <form:label class="form-label" path="description">
                    <spring:message code="edituser.form.description"/>
                </form:label>
                <spring:message code="edituser.form.bandDescriptionplaceholder" var="descriptionplaceholder"/>
                <form:textarea type="text" id="bandDescription" maxlength="500" placeholder="${descriptionplaceholder}"
                               class="form-input-application"
                               path="description"/>
                <form:errors path="description" element="p" cssClass="error"> </form:errors>
                <p class="error" id="wrongArtistDescription" style="display: none"><spring:message
                        code="edituser.form.invalidDescription"/></p>
            </div>


            <div class="select-div">
                <form:label class="form-label" path="musicGenres">
                    <spring:message code="profile.bandGenres"/>
                </form:label>
                <form:select
                        class="multiple-select"
                        path="musicGenres"
                        multiple="true"
                >
                    <form:option value="" disabled="true" selected="true"><spring:message code="edituser.musicGenres.maxSelect" arguments="30"/></form:option>

                    <c:forEach var="genre" items="${genreList}" varStatus="loop">
                        <form:option value="${genre.name}"><c:out value="${genre.name}"/></form:option>
                    </c:forEach>


                </form:select>
                <form:errors path="musicGenres" element="p" cssClass="error">
                </form:errors>
            </div>
            <div class="select-div">
                <form:label class="form-label" for="lookingFor" path="lookingFor"> <spring:message code="profile.bandRoles"/> </form:label>
                <form:select
                        class="multiple-select"
                        path="lookingFor"
                        multiple="true"
                >
                    <form:option value="" disabled="true" selected="true"><spring:message code="edituser.lookingFor.maxSelect" arguments="30"/></form:option>

                    <c:forEach var="role" items="${roleList}" varStatus="loop">
                        <form:option value="${role.name}"><c:out value="${role.name}"/></form:option>
                    </c:forEach>

                </form:select>
                <form:errors path="lookingFor" element="p" cssClass="error">
                </form:errors>
            </div>
            <div class="end-button-div">
                <spring:message code="button.cancel" var="cancel"/>
                <button
                        onclick="openConfirmation()"
                        type="button"
                        form="bandEditForm"
                        value="submit"
                        class="save-button"
                >
                    <spring:message code="edituser.saveChangesBtn"/>
                </button>
                <a href="<c:url value="/profile"/>" class="cancel-button">
                    <spring:message code="button.cancel" />
                </a>
            </div>
            <spring:message code="editConfirmationModal.title" var="modalTitle"/>
            <spring:message code="editConfirmationModal.deleteAudition" var="modalHeading"/>
            <spring:message code="editConfirmationModal.confirmationQuestion" var="confirmationQuestion"/>
            <jsp:include page="../components/confirmationModal.jsp">
                <jsp:param name="modalTitle" value="${modalTitle}" />
                <jsp:param name="isDelete" value="${false}" />
                <jsp:param name="modalHeading" value="${modalHeading}" />
                <jsp:param name="confirmationQuestion" value="${confirmationQuestion}" />
                <jsp:param name="action" value="${editProfileUrl}" />
            </jsp:include>
        </form:form>

            <div id="snackbar"><spring:message code="snackbar.message"/></div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>