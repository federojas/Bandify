<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<body>
<c:url value="/login" var="loginUrl" />
<form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
    <div>
        <label for="email">Email: </label>
        <input id="email" name="email" type="text"/>
    </div>
    <div>
        <label for="password">Password: </label>
        <input id="password" name="password" type="password"/>
    </div>
    <div>
       <label> <spring:message  text="Remember me: " />  <input name="rememberme" type="checkbox"/>  </label>
    </div>
    <div>
        <input type="submit" value="Log in"/>
    </div>
</form>
</body>
</html>
