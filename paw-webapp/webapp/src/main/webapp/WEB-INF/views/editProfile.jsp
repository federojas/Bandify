<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <title><spring:message code="edituser.title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <script src="<c:url value="/resources/js/editProfile.js"/>"></script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${6}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<h1 class="editProfile-title"><spring:message code="edituser.title" /></h1>
<div class="editProfile-box">
    <c:url value="/profile/edit" var="editProfileUrl"/>

    <%--@elvariable id="editUserForm" type="ar.edu.itba.paw.webapp.form.UserEditForm"--%>
    <form:form method="post" acceptCharset="utf-8" modelAttribute="userEditForm"
               action="${editProfileUrl}" id="editUserForm" enctype="multipart/form-data">
        <div>
            <form:label class="form-label" path="name">
                <spring:message code="register.form.name"/>
            </form:label>
            <spring:message code="register.form.nameplaceholder" var="nameplaceholder"/>
            <form:input type="text" id="artistName" maxlength="50" placeholder="${nameplaceholder}" class="form-input"
                        path="name"/>
            <form:errors path="name" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistName" style="display: none"><spring:message
                    code="register.form.invalidName"/></p>

        </div>

        <div id="surname-div">
            <form:label class="form-label" path="surname">
                <spring:message code="register.form.surname"/>
            </form:label>
            <spring:message code="register.form.surnameplaceholder" var="surnameplaceholder"/>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="surname"/>
            <form:errors path="surname" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistSurname" style="display: none"><spring:message
                    code="register.form.invalidSurname"/></p>
        </div>

        <div>
            <form:label class="form-label" path="profileImage" >
                <spring:message code="editProfile.form.image"/>
            </form:label>
            <br/>
            <div class = "editProfilePicture">
                <spring:message code="profile.img.alt" var="img"/>
                <img src="<c:url value="/user/${user.id}/profile-image"/>" class="editProfileImg" alt="${img}"/>
                <form:input id="selectImage" type="file" path="profileImage" accept="image/png, image/jpeg" />
                <form:errors path="profileImage" element="p" cssClass="error"/>
            </div>
        </div>

        <div>
            <form:label class="form-label" path="description">
                <spring:message code="edituser.form.description"/>
            </form:label>
            <spring:message code="edituser.form.descriptionplaceholder" var="descriptionplaceholder"/>
            <form:textarea type="text" id="artistDescription" maxlength="500" placeholder="${descriptionplaceholder}"
                           class="form-input"
                           path="description"/>
            <form:errors path="description" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistDescription" style="display: none"><spring:message
                    code="edituser.form.invalidDescription"/></p>
        </div>


        <div>
            <form:label class="form-label" path="musicGenres">
                <spring:message code="welcome.form.musicGenres"/>
            </form:label>
            <form:label class="form-label-legend" for="musicGenres" path="musicGenres"> <spring:message code="audition.form.musicGenres.maxSelect"/> </form:label>
            <form:select
                    class="multiple-select form-input"
                    path="musicGenres"
                    multiple="true"
            >
                <c:forEach var="userGenre" items="${userGenres}" varStatus="loop">
                    <form:option value="${userGenre.name}" selected="true">
                        ${userGenre.name}
                    </form:option>
                </c:forEach>
                <c:forEach var="genre" items="${genreList}" varStatus="loop">
                        <form:option value="${genre.name}">
                            ${genre.name}
                        </form:option>
                </c:forEach>


            </form:select>
            <form:errors path="musicGenres" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" for="lookingFor" path="lookingFor"> <spring:message code="welcome.form.lookingFor"/> </form:label>
            <form:label class="form-label-legend" for="lookingFor" path="lookingFor"> <spring:message code="audition.form.lookingFor.maxSelect"/> </form:label>
            <form:select
                    class="multiple-select form-input"
                    path="lookingFor"
                    multiple="true"
            >
                <c:forEach var="userRole" items="${userRoles}" varStatus="loop">
                    <form:option value="${userRole.name}" selected="true">
                        ${userRole.name}
                    </form:option>
                </c:forEach>
                <c:forEach var="role" items="${roleList}" varStatus="loop">
                    <form:option value="${role.name}"> ${role.name} </form:option>
                </c:forEach>

            </form:select>
            <form:errors path="lookingFor" element="p" cssClass="error">
            </form:errors>
        </div>
    </form:form>


    <div class="end-button-div">
        <button
                type="submit"
                form="editUserForm"
                value="submit"
                class="purple-button"
        >
            <spring:message code="edituser.saveChangesBtn"/>
        </button>
    </div>
</div>
</body>
</html>
