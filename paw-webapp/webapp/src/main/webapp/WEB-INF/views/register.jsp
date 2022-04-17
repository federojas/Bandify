<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<body>
<c:url value="/register" var="registerUrl" />
<form action="${registerUrl}" method="post" enctype="application/x-www-form-urlencoded">
    <div>
        <input type="submit" value="Register"/>
    </div>
</form>
</body>
</html>