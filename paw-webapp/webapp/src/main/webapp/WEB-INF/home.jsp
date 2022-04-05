<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<h2>Audicion: <c:out value="${audition.musicGenres[0]}"/> </h2>
<c:url value="/create" var="postPath"/>
<form:form modelAttribute="auditionForm" action="${postPath}" method="post">
    <div>
        <form:label path="title">Title: </form:label>
        <form:input type="text" path="title"/>
        <form:errors path="title" element="p"/>
    </div>
    <div>
        <form:label path="description">Description: </form:label>
        <form:input type="description" path="description" />
        <form:errors path="description" element="p"/>
    </div>
    <div>
        <form:label path="location">Location: </form:label>
        <form:input type="location" path="location"/>
        <form:errors path="location" element="p"/>
    </div>
    <div>
        <label for="musicGenres">Music Genres: </label>
        <form:select path="musicGenres" multiple="true">
            <form:option value="rock">Rock</form:option>
            <form:option value="cumbia">Cumbia</form:option>
        </form:select>
    </div>
    <div>
        <label for="lookingFor">Looking for: </label>
        <form:select path="lookingFor" multiple="true">
            <form:option value="guitarrista">Guitarrista</form:option>
            <form:option value="baterista">Baterista</form:option>
        </form:select>
    </div>
    <div>
        <input type="submit" value="Register!"/>
    </div>
</form:form>
</body>
</html>