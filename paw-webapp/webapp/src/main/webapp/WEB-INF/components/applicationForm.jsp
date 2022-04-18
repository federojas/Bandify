<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Questrial"
    />
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/forms.css" />">
</head>
<script>
    function check(){
        name=document.getElementById("name").value;
        email=document.getElementById("email").value;
        message=document.getElementById("message").value;;
        validForm=true;
        sendForm=true;
        if(name.length<=0 || name.length>50 || email.length<=0 || email.length>250||message.length<=0||message.length>250){
            valid=false;
        }
        if(typeof email!='string'||typeof message != 'string'||typeof name != 'string'){
            sendForm=false;
        }
        if(typeof message != 'string'){
            valid=false;
        }
        if(!valid){
            snackbarMessage()
        }
        return sendForm;
    }
    function snackbarMessage() {
        var x = document.getElementById("snackbar");
        x.className = "show";
        setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
    }
</script>
<style>
    #snackbar {
        visibility: hidden;
        min-width: 250px;
        margin-left: -125px;
        background-color: red;
        color: #fff;
        text-align: center;
        border-radius: 2px;
        padding: 16px;
        position: fixed;
        z-index: 1;
        left: 50%;
        bottom: 30px;
        font-size: 17px;
    }

    #snackbar.show {
        visibility: visible;
        -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
        animation: fadein 0.5s, fadeout 0.5s 2.5s;
    }

    @-webkit-keyframes fadein {
        from {bottom: 0; opacity: 0;}
        to {bottom: 30px; opacity: 1;}
    }

    @keyframes fadein {
        from {bottom: 0; opacity: 0;}
        to {bottom: 30px; opacity: 1;}
    }

    @-webkit-keyframes fadeout {
        from {bottom: 30px; opacity: 1;}
        to {bottom: 0; opacity: 0;}
    }

    @keyframes fadeout {
        from {bottom: 30px; opacity: 1;}
        to {bottom: 0; opacity: 0;}
    }
</style>
<body>
<div class="applicationForm">
    <c:url value="/apply" var="postularmeUrl">
        <c:param name="id" value="${param.auditionFormId}"/>
    </c:url>

    <%--@elvariable id="applicationForm" type="ar.edu.itba.paw.webapp.form.ApplicationForm"--%>
    <form:form acceptCharset="utf-8" modelAttribute="applicationForm"
               action="${postularmeUrl}" method="post" id="form">
        <div>
            <form:label class="form-label"  path="name">
                <spring:message code="application.form.name"/>
            </form:label>
            <spring:message code="application.form.name.placeholder" var="nameplaceholder" />
            <form:input type="text" maxlength="50" id="name" class="form-input" placeholder="${nameplaceholder}"
                        path="name"/>

            <form:errors path="name" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" path="email">
                <spring:message code="application.form.email"/>
            </form:label>
            <spring:message code="application.form.email.placeholder" var="emailplaceholder" />
            <form:input type="email" id="email" maxlength="254" placeholder="${emailplaceholder}" class="form-input"
                        path="email"/>

            <form:errors path="email" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" path="message">
                <spring:message code="application.form.message"/>
            </form:label>
            <spring:message code="application.form.message.placeholder" var="messageplaceholder" />
            <form:textarea type="text" id="message" maxlength="300" placeholder="${messageplaceholder}" class="form-input"
                           path="message"/>

            <form:errors path="message" element="p" cssClass="error">
            </form:errors>
        </div>
        <div class="end-button-div">
            <button
                    type="submit"
                    value="submit"
                    onclick="return check()"
                    class="purple-button">
                <spring:message code="application.form.apply"/>
            </button>
        </div>
    </form:form>
    <div id="snackbar"><spring:message code="snackbar.message"/></div>
</div>
</body>

</html>
