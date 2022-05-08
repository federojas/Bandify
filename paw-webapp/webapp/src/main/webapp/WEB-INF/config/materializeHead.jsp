<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel = "stylesheet"
      href = "https://fonts.googleapis.com/icon?family=Material+Icons">
<%--<link rel = "stylesheet"--%>
<%--      href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">--%>
<script type = "text/javascript"
        src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/form-materialize.css"/>" />
<script src = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script>
<script>
    $(document).ready(function() {
        $('select').material_select();
    });
</script>
<style>
    .parallax-container {
        height: 500px;
    }
</style>
<title><spring:message code="title.materializeHead"/> </title>